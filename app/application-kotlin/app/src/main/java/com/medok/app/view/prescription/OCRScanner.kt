package com.medok.app.view.prescription

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.ui.text.font.FontStyle
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.medok.app.data.objectbox.LocalDatabaseManager
import com.medok.app.model.Doctor
import com.medok.app.model.Medicine
import com.medok.app.model.prescription.PrescribedMedicine
import com.medok.app.model.prescription.SimplePrescription
import com.medok.app.ui.components.button.CustomHybridButton
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.Txt
import kotlinx.datetime.LocalDate
import java.io.File
import java.util.regex.Pattern

@Composable
fun OCRScanner(
    onShowDialog: (SimplePrescription) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val localBD = LocalDatabaseManager


    /**
     * Instancie le texte en une prescription
     */
    fun instantiateText(text: String) : SimplePrescription {
        val reDoc = Regex("""(Docteur|Dr) ([\p{L}]+) ([\p{L}]+)""")
        val reAddress = Regex("""\d+, ([\p{L}]+(?: [\p{L}]+)*)""")
        val rePhoneNumber = Regex("""Cabinet : \d{2}(?:\s\d{2}){4}""")
        val reRedactionDate = Regex("""([\p{L}]+(?: [\p{L}]+)*), le (\d{2}\/\d{2}\/\d{4})""")
        val reMedicine = Regex("""\d+(]|\.|\)).*\n.*""")

        // Get results
        val resDoc = reDoc.find(text)
        val resAddress = reAddress.find(text)
        val resPhoneNumber = rePhoneNumber.find(text)
        val resRedactionDate = reRedactionDate.find(text)
        val resMedicine = reMedicine.findAll(text).toList()

        //Instantiate

        val lst = mutableListOf<PrescribedMedicine>()

        if (resMedicine.size > 0) {
            for (med in resMedicine) {
                val s = med.value.split("\n")

                //remove clutter on name (the numbering)
                var medName = s[0]
                val reMedNumber = Regex("""\d+(]|\.|\)) """)
                val resMedNumber = reMedNumber.find(medName)
                if (resMedNumber != null) {
                    medName = medName.replace(resMedNumber.value,"")
                }

                //retrieve posology
                var posology = if (s.size == 0) "" else s[1]

                val newMed = Medicine(name = medName)
                localBD.addMedicine(newMed)

                val prescribedMed = PrescribedMedicine(posology = posology)
                prescribedMed.medicine.target = newMed
                lst.add(prescribedMed)
                localBD.addPrescribedMedicine(prescribedMed)
            }
        }

        val prescription = SimplePrescription(
            doctorName = resDoc?.value ?: "",
            doctorPhoneNumber = if (resPhoneNumber != null)
                resPhoneNumber.value.removePrefix("Cabinet : ")
            else
                "",
            address = resAddress?.value ?: "",
            redactionDate = if (resRedactionDate != null)
                resRedactionDate.value.split(
                    regex= Pattern.compile("""([\p{L}]+(?: [\p{L}]+)*), le """)
                ).last()
            else
                ""
        )
        prescription.medicines.addAll(lst)

        return prescription
    }

    /**
     * Lit le texte d'une image
     */
    fun processImage(inputImage: InputImage) {

        Toast.makeText(context, "Analyse de l'image en cours...", Toast.LENGTH_SHORT).show()

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->

                if (visionText.text.isEmpty()) {
                    Toast.makeText(context, "Pas de texte reconnu", Toast.LENGTH_SHORT).show()

                    val prescription = SimplePrescription( //placeholder class
                        doctorName = "",
                        doctorPhoneNumber = "",
                        address = "",
                        redactionDate = ""
                    )
                    localBD.getPrescriptionBox().put(prescription)

                    onShowDialog(prescription)
                } else {
                    Toast.makeText(context, "Lecture de l'image en cours...", Toast.LENGTH_SHORT).show()

                    val prescription = instantiateText(visionText.text)
                    localBD.getPrescriptionBox().put(prescription)

                    onShowDialog(prescription)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Erreur : ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * L'activité qui lit le texte de l'image
     */
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                processImage(InputImage.fromFilePath(context, uri))
            } ?: result.data?.extras?.get("data")?.let { bitmap ->
                if (bitmap is Bitmap) {
                    processImage(InputImage.fromBitmap(bitmap, 0))
                }
            }
        }
    }

    /**
     * Lance l'activité
     */
    fun launchImagePicker() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }

        val chooserIntent = Intent.createChooser(pickPhotoIntent, "Select an option")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))

        launcher.launch(chooserIntent)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Text(
            "Avertissement : La qualité de l'image influence la qualité du pré-remplissage automatique de l'ordonnance" ,
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                color = Txt.copy(alpha = 0.75f)
            ),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        CustomHybridButton(
            text = "Ajouter une ordonnance",
            icon = rememberVectorPainter(Icons.Default.DocumentScanner),
            onClick = { launchImagePicker() },
            buttonColor = GreenLight
        )
    }
}