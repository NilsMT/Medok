package com.medok.app.view.prescription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Deselect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.medok.app.data.objectbox.LocalDatabaseManager
import com.medok.app.model.Medicine
import com.medok.app.model.prescription.PrescribedMedicine
import com.medok.app.model.prescription.SimplePrescription
import com.medok.app.model.prescription.SimplePrescription_
import com.medok.app.ui.components.ListInput
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.button.CustomHybridButton
import com.medok.app.ui.components.button.CustomIconButton
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt
import com.medok.app.ui.theme.WhiteLight

@Composable
fun PrescriptionDialog(
    isInitializingVariant: Boolean,
    prescription: SimplePrescription,
    onOk: (SimplePrescription) -> Unit,
    onDismiss: () -> Unit,
    onDelete: (SimplePrescription) -> Unit = {},
) {
    val localDB = LocalDatabaseManager

    // Manage each field's state individually
    var doctor = remember { mutableStateOf(prescription.doctorName) }
    var doctorPhoneNumber = remember { mutableStateOf(prescription.doctorPhoneNumber) }
    var address = remember { mutableStateOf(prescription.address) }
    var redactionDate = remember { mutableStateOf(prescription.redactionDate) }

    // Remember the list of medicines and update it accordingly
    val medicines = remember { mutableStateListOf(*prescription.medicines.toTypedArray()) }

    // Remember that we pass the medicines list back to the parent composable when it updates
    val onMedicinesChange = rememberUpdatedState(onOk)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ordonnance ${prescription.id}") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState()) //make only the list scrollable
                    .fillMaxWidth()
            ) {
                // Doctor
                CustomTextField(
                    label = "Docteur",
                    value = doctor.value,
                    onValueChange = { doctor.value = it }
                )

                // Address
                CustomTextField(
                    label = "Adresse",
                    value = address.value,
                    onValueChange = { address.value = it }
                )

                // Doctor's phone number
                CustomTextField(
                    label = "Numéro de téléphone",
                    value = doctorPhoneNumber.value,
                    onValueChange = { doctorPhoneNumber.value = it }
                )

                // Redaction date
                CustomTextField(
                    label = "Date de rédaction",
                    value = redactionDate.value,
                    onValueChange = { redactionDate.value = it }
                )

                Spacer(Modifier.height(8.dp))

                // Medicines list
                if (medicines.size > 0) {
                    Text("Médicaments")
                }

                medicines.forEachIndexed { index, prescribedMedicine ->
                    MedicineInputFields(
                        medicine = prescribedMedicine,
                        onNameChange = { newName ->
                            val updatedMedicine = prescribedMedicine.medicine.target.copy(name = newName)
                            localDB.addMedicine(updatedMedicine)

                            val cp = prescribedMedicine.copy()
                            cp.medicine.target = updatedMedicine

                            medicines.removeAt(index)
                            medicines.add(index, cp) // Forces Compose to see modification
                        },
                        onPosologyChange = { newPosology ->
                            val updatedMedicine = prescribedMedicine.copy(posology = newPosology)
                            updatedMedicine.medicine = prescribedMedicine.medicine
                            medicines[index] = updatedMedicine
                        },
                        onRemove = {
                            medicines.removeAt(index)
                        }
                    )
                }

                CustomHybridButton(
                    "Ajouter un médicament",
                    rememberVectorPainter(Icons.Default.Add),
                    onClick = {
                        val newMed = Medicine(name = "")
                        localDB.addMedicine(newMed)

                        val newPrescribedMed = PrescribedMedicine(posology = "")
                        newPrescribedMed.medicine.target = newMed
                        localDB.addPrescribedMedicine(newPrescribedMed)

                        medicines.add(newPrescribedMed)
                    }
                )
            }
        },
        confirmButton = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isInitializingVariant) {
                        CustomButton(
                            text = "Annuler",
                            onClick = onDismiss,
                            buttonColor = RedLight
                        )
                    } else {
                        CustomButton(
                            text = "Supprimer",
                            onClick = {
                                localDB.deletePrescription(prescription.id)
                                onDelete(prescription)
                            },
                            buttonColor = RedLight
                        )
                    }

                    CustomButton(
                        text = "Ok",
                        onClick = {
                            // Pass updated prescription to onOk with updated medicines
                            localDB.getPrescribedMedicineBox().remove(prescription.medicines)

                            medicines.forEach { med ->
                                localDB.addMedicine(med.medicine.target)
                                localDB.addPrescribedMedicine(med)
                                localDB.addPrescribedMedToPrescription(prescription.id, med)
                            }

                            prescription.medicines.clear()
                            prescription.medicines.addAll(medicines)
                            prescription.doctorName = doctor.value
                            prescription.doctorPhoneNumber = doctorPhoneNumber.value
                            prescription.address = address.value
                            prescription.redactionDate = redactionDate.value

                            localDB.addPrescription(prescription)
                            onMedicinesChange.value(prescription)
                        },
                        buttonColor = GreenLight
                    )
                }
            }
        }
    )
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Txt,
            unfocusedBorderColor = Txt,
            focusedLabelColor = Txt,
            unfocusedLabelColor = Txt,
        )
    )
}

@Composable
fun MedicineInputFields(
    medicine: PrescribedMedicine,
    onNameChange: (String) -> Unit,
    onPosologyChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Txt, RoundedCornerShape(12.dp))
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // Medicine Name
            CustomTextField(
                label = "Nom du médicament",
                value = medicine.medicine.target?.name ?: "",
                onValueChange = onNameChange
            )

            // Medicine Posology
            CustomTextField(
                label = "Posologie",
                value = medicine.posology,
                onValueChange = onPosologyChange
            )

            // Delete Button
            CustomHybridButton(
                "Supprimer le médicament",
                rememberVectorPainter(Icons.Default.DeleteForever),
                buttonColor = RedLight,
                onClick = onRemove
            )
        }
    }
}