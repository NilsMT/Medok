package com.medok.app.view.reminders

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.applicationsae.model.MedicalReminderModele
import com.medok.app.data.loadReminders
import com.medok.app.data.saveReminders
import com.medok.app.ui.theme.Txt


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun MedicalRemindersContent(navController: NavHostController) {
    val listMedicines = listOf("Doliprane", "Aspirine", "Ibuprofène")
    val listPathologies = listOf("Grippe", "Migraine", "Diabète")

    val context = LocalContext.current
    var reminders by remember {
        mutableStateOf(loadReminders(context))
    }
    var showAddDialog by remember { mutableStateOf(false) }

    var newId by remember { mutableIntStateOf(0) }
    var newName by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }
    var newHour by remember { mutableStateOf("") }
    var newMedicines by remember { mutableStateOf(listMedicines.associateWith { false }.toMutableMap()) }
    var newPathologies by remember { mutableStateOf(listPathologies.associateWith { false }.toMutableMap()) }
    var newRecurrenceDateType by remember { mutableStateOf("") }
    var newRecurrenceActive by remember { mutableStateOf(false) }
    var newEveryRecurrence by remember { mutableStateOf("") }
    var newRadioSelectedOption by remember { mutableStateOf("") }
    var newEndDate by remember { mutableStateOf("") }
    var newEndTimes by remember { mutableStateOf("") }
    var newActivated by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rappels médicaux",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Txt,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SearchAndNewReminder(
            text = "Ajouter un rappel",
            onSearchChange = { query -> searchText = query },
            onNewReminder = {
                newId = reminders.size
                newMedicines = listMedicines.associateWith { false }.toMutableMap()
                newPathologies = listPathologies.associateWith { false }.toMutableMap()
                newName = ""
                newDate = ""
                newHour = ""
                newRecurrenceDateType = ""
                newEveryRecurrence = ""
                newRadioSelectedOption = ""
                newEndDate = ""
                newEndTimes = ""
                newRecurrenceActive = false
                newActivated = false
                showAddDialog = true
            }
        )

        LaunchedEffect(reminders) {
            saveReminders(context, reminders)
        }

        val filteredReminders = reminders.filter {it.name.contains(searchText, ignoreCase = true)}

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            filteredReminders.forEach { reminder ->
                MedicalRemindersBox(
                    reminder = reminder,
                    onSave = { updatedReminder ->
                        reminders =
                            reminders.map { if (it.id == updatedReminder.id) updatedReminder else it }
                    },
                    onDelete = {
                        reminders = reminders.filter { it.id != reminder.id }
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        MedicalRemindersDialog(
            onDismiss = { showAddDialog = false },
            reminderId = reminders.size,
            currentMedicines = newMedicines,
            currentPathologies = newPathologies,
            currentName = newName,
            currentDate = newDate,
            currentHour = newHour,
            currentRecurrenceDateType = newRecurrenceDateType,
            currentEveryRecurrence = newEveryRecurrence,
            currentSelectedRadioOption = newRadioSelectedOption,
            currentEndDate = newEndDate,
            currentEndTime = newEndTimes,
            currentRecurrenceActive = newRecurrenceActive,
            onSave = { updatedMedicines, updatedPathologies, updatedName, updatedDate, updatedHour, updatedRecurrenceDateType, updatedEveryRecurrence, updatedRadioSelectedOption, updatedEndDate, updatedEndTimes, updatedRecurrenceActive ->
                val newReminder = MedicalReminderModele(
                    id = reminders.size,
                    name = updatedName,
                    date = updatedDate,
                    hour = updatedHour,
                    medicines = updatedMedicines,
                    pathologies = updatedPathologies,
                    recurrenceDateType = updatedRecurrenceDateType,
                    everyRecurrence = updatedEveryRecurrence,
                    radioSelectedOption = updatedRadioSelectedOption,
                    endDate = updatedEndDate,
                    endTimes = updatedEndTimes,
                    activated = newActivated,
                    recurrenceActive = updatedRecurrenceActive
                )
                reminders = reminders + newReminder
                saveReminders(context, reminders)
                showAddDialog = false
            },
            onDelete = {
                showAddDialog = false
            }
        )
    }
}


