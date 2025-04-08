package com.medok.app.view.prescription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.model.prescription.SimplePrescription
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.Txt
import com.medok.app.ui.theme.WhiteLight

@Composable
fun PrescriptionItem(
    prescription: SimplePrescription,
    onShowDialog: (SimplePrescription) -> Unit,
    onCreateReminder: (SimplePrescription) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Txt, RoundedCornerShape(12.dp))
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Ordonnance ${prescription.id}",color = Txt,
                style = MaterialTheme.typography.titleLarge
            )
            Modifier.padding(bottom = 8.dp)

            CustomButton(
                text="Modifier",
                onClick = { onShowDialog(prescription) }
            )
            CustomButton(
                "Créer un rappel",
                buttonColor = BlueLight,
                onClick = {
                    onCreateReminder(prescription)
                }
            )
            FakeOutlinedTextField("Docteur",prescription.doctorName)
            FakeOutlinedTextField("Adresse",prescription.address)
            FakeOutlinedTextField("Numéro de téléphone",prescription.doctorPhoneNumber)
            FakeOutlinedTextField("Date de rédaction",prescription.redactionDate)

            prescription.medicines.forEach{ med ->
                if (
                    med.medicine.target.toString().isNotBlank() &&
                    med.posology.isNotBlank()
                ) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }


                if (med.medicine.target.toString().isNotBlank()) {
                    Text("Nom: ", color = Txt, fontWeight = FontWeight.Bold)
                    Text(med.medicine.target.toString(), color = Txt)
                }

                if (med.posology.isNotBlank()) {
                    Text("Posologie: ", color = Txt, fontWeight = FontWeight.Bold)
                    Text(med.posology, color = Txt)
                }

            }
        }
    }
}

@Composable
fun FakeOutlinedTextField(inputName: String, value: String) {
    OutlinedTextField(
        value = value,

        //fake textField style

        singleLine = true,
        maxLines = 1,
        isError = false,
        onValueChange = {}, //ensure readOnly (just in case)
        readOnly = true, //readOnly
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            disabledLabelColor = Txt,
            disabledTextColor = Txt,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Txt,
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Txt,
            lineHeight = TextUnit.Unspecified,
            textDecoration = TextDecoration.None
        ),
        label = {
            Text(inputName)
        }
    )
}