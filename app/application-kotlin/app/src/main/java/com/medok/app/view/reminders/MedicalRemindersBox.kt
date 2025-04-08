package com.medok.app.view.reminders

import TimePickerField
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicationsae.model.MedicalReminderModele
import com.medok.app.controller.*
import com.medok.app.ui.components.ListInput
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.field.DatePickerField
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.RedDark
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt
import com.medok.app.ui.theme.WhiteDark
import com.medok.app.ui.theme.WhiteLight

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicalRemindersDialog(
    onDismiss: () -> Unit,
    reminderId: Int,
    currentName: String,
    currentDate: String,
    currentHour: String,
    currentMedicines: MutableMap<String, Boolean>,
    currentPathologies: MutableMap<String, Boolean>,
    currentRecurrenceDateType: String,
    currentEveryRecurrence: String,
    currentSelectedRadioOption: String,
    currentEndDate: String,
    currentEndTime: String,
    currentRecurrenceActive: Boolean,
    onSave: (MutableMap<String, Boolean>, MutableMap<String, Boolean>, String, String, String, String, String, String, String, String, Boolean) -> Unit,
    onDelete: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var date by remember { mutableStateOf(currentDate) }
    var hour by remember { mutableStateOf(currentHour) }
    val medicines by remember { mutableStateOf(currentMedicines) }
    val pathologies by remember { mutableStateOf(currentPathologies) }
    var recurrenceDateType by remember { mutableStateOf(currentRecurrenceDateType) }
    var everyRecurrence by remember { mutableStateOf(currentEveryRecurrence) }
    var isRecurrenceActive by remember { mutableStateOf(currentRecurrenceActive) }
    var selectedRadioOption by remember { mutableStateOf(currentSelectedRadioOption) }
    var dateFieldValue by remember { mutableStateOf(TextFieldValue(date)) }
    var hourFieldValue by remember { mutableStateOf(TextFieldValue(hour)) }
    var recurrenceEndingDate by remember { mutableStateOf(currentEndDate) }
    val recurrenceEndDateFieldValue by remember { mutableStateOf(TextFieldValue(currentEndDate)) }
    var recurrenceEndingTimes by remember { mutableStateOf(currentEndTime) }
    val recurrenceEndTimesFieldValue by remember { mutableStateOf(TextFieldValue(currentEndTime)) }
    val isFormValid = name.isNotEmpty() && date.isNotEmpty() && hour.length == 5 && medicines.isNotEmpty() && pathologies.isNotEmpty()
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Modifier le rappel",
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
                    label = { Text("Nom du rappel") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
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
                DatePickerField(
                    text = date,
                    onDateSelected = { selectedDate ->
                        date = selectedDate
                        dateFieldValue = TextFieldValue(selectedDate)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TimePickerField(
                    hour = hour,
                    onHourSelected = { selectedHour ->
                        hour = selectedHour
                        hourFieldValue = TextFieldValue(selectedHour)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isRecurrenceActive,
                        onCheckedChange = { isRecurrenceActive = it }
                    )
                    Text("Répéter la notification", modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Répétition chaque : ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = everyRecurrence,
                        onValueChange = { newValue ->
                            if (newValue.all { it.isDigit() }) {
                                everyRecurrence = newValue
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { if (everyRecurrence.isEmpty()) Text("X") else Text("") },
                        modifier = Modifier
                            .weight(1f)
                    )
                    val recurrenceOptions = listOf("minutes","heures","jours", "semaines", "mois", "années")
                    RecurrenceDropdown(
                        selectedRecurrence = recurrenceDateType,
                        options = recurrenceOptions,
                        onRecurrenceSelected = { recurrenceDateType = it },
                        modifier = Modifier.weight(3f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Se termine :",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                RadioButtonGroup(
                    fieldEndDate = recurrenceEndDateFieldValue,
                    fieldTimeDate = recurrenceEndTimesFieldValue,
                    selectedOption = selectedRadioOption,
                    onOptionSelected = { selectedRadioOption = it },
                    onEndDateChanged = { newDate -> recurrenceEndingDate = newDate },
                    onEndTimeChanged = { newTime -> recurrenceEndingTimes = newTime }
                )
                if (currentName.isNotEmpty()) {
                    TextButton(
                        onClick = {
                            cancelNotification(context, reminderId)
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
                    onSave(medicines, pathologies, name, date, hour, recurrenceDateType, everyRecurrence, selectedRadioOption, recurrenceEndingDate, recurrenceEndingTimes, isRecurrenceActive)
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



@Composable
fun RadioButtonGroup(
    fieldEndDate: TextFieldValue,
    fieldTimeDate: TextFieldValue,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onEndTimeChanged: (String) -> Unit
) {
    var fieldEndState by remember { mutableStateOf(fieldEndDate) }
    var fieldTimeState by remember { mutableStateOf(fieldTimeDate) }
    LaunchedEffect(fieldEndDate, fieldTimeDate) {
        fieldEndState = fieldEndDate
        fieldTimeState = fieldTimeDate
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOptionSelected("jamais") }
        ) {
            RadioButton(
                selected = ("jamais" == selectedOption),
                onClick = { onOptionSelected("jamais") }
            )
            Text(
                text = "Jamais",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOptionSelected("le JJ-MM-AAAA") }
        ) {
            RadioButton(
                selected = ("le JJ-MM-AAAA" == selectedOption),
                onClick = { onOptionSelected("le JJ-MM-AAAA") }
            )
                DatePickerField(
                    text = fieldEndState.text,
                    onDateSelected = { selectedDate ->
                        fieldEndState = TextFieldValue(selectedDate)
                        onEndDateChanged(selectedDate)
                    }
                )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOptionSelected("après X fois") }
        ) {
            RadioButton(
                selected = ("après X fois" == selectedOption),
                onClick = { onOptionSelected("après X fois") }
            )
            Text(
                text = "Après",
                modifier = Modifier.padding(start = 8.dp)
            )
            OutlinedTextField(
                value = fieldTimeState,
                onValueChange = { newValue ->
                    if (newValue.text.all { it.isDigit() }) {
                        fieldTimeState = TextFieldValue(
                            text = newValue.text,
                            selection = TextRange(newValue.text.length)
                        )
                        onEndTimeChanged(newValue.text)
                    }
                },
                label = { if (fieldTimeState.text == "") Text("X") else Text("") },
                modifier = Modifier
                    .width(80.dp)
                    .padding(start = 8.dp)
            )
            Text(
                text = "fois",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }
}

@Composable
fun RecurrenceDropdown(
    selectedRecurrence: String,
    options: List<String>,
    onRecurrenceSelected: (String) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedRecurrence) }
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            label = { Text("Récurrence") },
            trailingIcon = {
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        expanded = false
                        onRecurrenceSelected(option)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicalRemindersBox(
    reminder: MedicalReminderModele,
    onSave: (MedicalReminderModele) -> Unit,
    onDelete: (Int) -> Unit
) {
    var act by remember { mutableStateOf(reminder.activated) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                text = reminder.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Txt,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Traitements(s) associé(s) :", fontWeight = FontWeight.SemiBold)
                Text(
                    text = reminder.medicines.filter { it.value }.keys.joinToString(", "),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(text = "Pathologie(s) associée(s) :", fontWeight = FontWeight.SemiBold)
                Text(
                    text = reminder.pathologies.filter { it.value }.keys.joinToString(", "),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Date du rappel :", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${reminder.date} ${reminder.hour}", color = MaterialTheme.colorScheme.onSurface)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                CustomButton(
                    if (act) "Désactiver la notification" else "Activer la notification",
                    onClick = {
                        act = !act
                        onSave(reminder.copy(activated = act))
                        if (act) {
                            Toast.makeText(context, "Notification activée", Toast.LENGTH_SHORT).show()
                            scheduleNotification(
                                context = context,
                                hour = reminder.hour.split(":")[0].toInt(),
                                minute = reminder.hour.split(":")[1].toInt(),
                                date = reminder.date,
                                name = reminder.name,
                                pathologies = reminder.pathologies,
                                medicines = reminder.medicines,
                                reminderId = reminder.id,
                                recurrenceActive = reminder.recurrenceActive,
                                recurrenceDateType = reminder.recurrenceDateType,
                                everyRecurrence = reminder.everyRecurrence,
                                selectedRadio = reminder.radioSelectedOption,
                                endDate = reminder.endDate,
                                endTimes = reminder.endTimes
                            )
                        } else {
                            Toast.makeText(context, "Notification désactivée", Toast.LENGTH_SHORT).show()
                            cancelNotification(context, reminder.id)
                        }
                    },
                    buttonColor = if (act) WhiteDark else GreenLight
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    "Modifier",
                    onClick = { showDialog = true },
                    buttonColor = BlueLight,
                )
            }
        }
    }
    if (showDialog) {
        MedicalRemindersDialog(
            onDismiss = { showDialog = false },
            reminderId = reminder.id,
            currentName = reminder.name,
            currentDate = reminder.date,
            currentHour = reminder.hour,
            currentMedicines = reminder.medicines,
            currentRecurrenceDateType = reminder.recurrenceDateType,
            currentEveryRecurrence = reminder.everyRecurrence,
            currentPathologies = reminder.pathologies,
            currentSelectedRadioOption = reminder.radioSelectedOption,
            currentEndDate = reminder.endDate,
            currentEndTime = reminder.endTimes,
            currentRecurrenceActive = reminder.recurrenceActive,
            onSave = { updatedMedicines, updatedPathologies, updatedName, updatedDate, updatedHour, updatedRecurrence, updatedEveryRecurrence, updatedRadioSelectedOption, updatedEndDate, updatedEndTimes, updatedRecurrenceActive ->
                val updatedReminder = reminder.copy(
                    name = updatedName,
                    date = updatedDate,
                    hour = updatedHour,
                    medicines = updatedMedicines,
                    pathologies = updatedPathologies,
                    recurrenceDateType = updatedRecurrence,
                    everyRecurrence = updatedEveryRecurrence,
                    radioSelectedOption = updatedRadioSelectedOption,
                    endDate = updatedEndDate,
                    endTimes = updatedEndTimes,
                    recurrenceActive = updatedRecurrenceActive
                )
                onSave(updatedReminder)
                showDialog = false
            },
            onDelete = {
                onDelete(reminder.id)
                showDialog = false
            }
        )
    }
}

