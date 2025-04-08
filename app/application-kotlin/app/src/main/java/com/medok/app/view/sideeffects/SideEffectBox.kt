package com.medok.app.view.sideeffects

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.model.SideEffectModele
import com.medok.app.ui.components.ListInput
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.field.DatePickerField
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.RedDark
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt
import com.medok.app.ui.theme.WhiteLight

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SideEffectDialog(
    onDismiss: () -> Unit,
    currentName: String,
    currentDate: String,
    currentMedicines: MutableMap<String, Boolean>,
    currentPathologies: MutableMap<String, Boolean>,
    currentEffects: MutableMap<String, Boolean>,
    onSave: (MutableMap<String, Boolean>, MutableMap<String, Boolean>, MutableMap<String, Boolean>, String, String) -> Unit,
    onDelete: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var date by remember { mutableStateOf(currentDate) }
    val medicines by remember { mutableStateOf(currentMedicines) }
    val pathologies by remember { mutableStateOf(currentPathologies) }
    val effects by remember { mutableStateOf(currentEffects) }
    var dateFieldValue by remember { mutableStateOf(TextFieldValue(date)) }
    val isFormValid = name.isNotEmpty() && date.isNotEmpty() && medicines.isNotEmpty() && pathologies.isNotEmpty() && effects.isNotEmpty()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Modifier l'effet secondaire",
                style = MaterialTheme.typography.titleLarge)
            Modifier.padding(bottom = 8.dp)
        },
        text = {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom de l'effet secondaire") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                DatePickerField(
                    text = date,
                    onDateSelected = { selectedDate ->
                        date = selectedDate
                        dateFieldValue = TextFieldValue(selectedDate)
                    }
                )
                ListInput(
                    "Traitements associés",
                    medicines,
                    true
                )
                ListInput(
                    "Pathologies associées",
                    pathologies,
                    true
                )
                ListInput(
                    "Effets secondaires associés",
                    effects,
                    true
                )
                if (currentName.isNotEmpty()) {
                    TextButton(
                        onClick = {
                            onDelete()
                            onDismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = RedDark,
                            contentColor = WhiteLight
                        )
                    ) {
                        Text("Supprimer")
                    }
                }
            }
        },
        confirmButton = {
            CustomButton(
                "Sauvegarder",
                {
                    onSave(medicines, pathologies, effects, name, date)
                },
                buttonColor = BlueLight,
                enabled = isFormValid
            )
        },
        dismissButton = {
            CustomButton(
                "Annuler",
                onClick = onDismiss,
                buttonColor = RedLight
            )
        }
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SideEffectBox(
    sideEffect: SideEffectModele,
    onSave: (SideEffectModele) -> Unit,
    onDelete: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, Txt, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = sideEffect.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Txt,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Traitement(s) associé(s) :", fontWeight = FontWeight.SemiBold)
                Text(
                    text = sideEffect.medicines.filter { it.value }.keys.joinToString(", "),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(text = "Pathologie(s) associée(s) :", fontWeight = FontWeight.SemiBold)
                Text(
                    text = sideEffect.pathologies.filter { it.value }.keys.joinToString(", "),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(text = "Effets secondaire(s) associée(s) :", fontWeight = FontWeight.SemiBold)
                Text(
                    text = sideEffect.effects.filter { it.value }.keys.joinToString(", "),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Date de l'effet secondaire :", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = sideEffect.date, color = MaterialTheme.colorScheme.onSurface)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                "Modifier",
                onClick = { showDialog = true },
                buttonColor = BlueLight
            )
        }
    }
    if (showDialog) {
        SideEffectDialog(
            onDismiss = { showDialog = false },
            currentName = sideEffect.name,
            currentDate = sideEffect.date,
            currentMedicines = sideEffect.medicines,
            currentPathologies = sideEffect.pathologies,
            currentEffects = sideEffect.effects,
            onSave = { updatedMedicines, updatedPathologies, updatedSideEffects, updatedName, updatedDate ->
                val updatedSideEffect = sideEffect.copy(
                    name = updatedName,
                    date = updatedDate,
                    medicines = updatedMedicines,
                    pathologies = updatedPathologies,
                    effects = updatedSideEffects
                )
                onSave(updatedSideEffect)
                showDialog = false
            },
            onDelete = {
                onDelete(sideEffect.id)
                showDialog = false
            }
        )
    }
}

