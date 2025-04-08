package com.medok.app.view.sideeffects

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.medok.app.data.loadSideEffects
import com.medok.app.data.saveSideEffects
import com.medok.app.model.SideEffectModele
import com.medok.app.ui.theme.Txt


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun SideEffectsJournalContent(navController: NavHostController) {
    val listMedicines = listOf("Doliprane", "Aspirine", "Ibuprofène")
    val listPathologies = listOf("Grippe", "Migraine", "Diabète")
    val listEffects = listOf("Nausée", "Vomissement", "Maux de tête")
    val context = LocalContext.current
    var sideEffects by remember {
        mutableStateOf(loadSideEffects(context))
    }

    var newId by remember { mutableIntStateOf(0) }
    var newName by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }
    var newMedicines by remember { mutableStateOf(listMedicines.associateWith { false }.toMutableMap()) }
    var newPathologies by remember { mutableStateOf(listPathologies.associateWith { false }.toMutableMap()) }
    var newSideEffects by remember { mutableStateOf(listEffects.associateWith { false }.toMutableMap()) }
    var searchText by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Journal des",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Txt
        )
        Text(
            text = "effets secondaires",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Txt,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SearchAndNewSideEffect(
            text = "Ajouter un effet secondaire",
            onSearchChange = { query -> searchText = query },
            onNewSideEffect = {
                newId = sideEffects.size
                newName = ""
                newDate = ""
                newMedicines = listMedicines.associateWith { false }.toMutableMap()
                newPathologies = listPathologies.associateWith { false }.toMutableMap()
                newSideEffects = listEffects.associateWith { false }.toMutableMap()
                showAddDialog = true
            })

        LaunchedEffect(sideEffects) {
            saveSideEffects(context, sideEffects)
        }

        val filteredSideEffects = sideEffects.filter {it.name.contains(searchText, ignoreCase = true)}

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            filteredSideEffects.forEach { sideEffect ->
                SideEffectBox(
                    sideEffect = sideEffect,
                    onSave = { updatedSideEffect ->
                        sideEffects =
                            sideEffects.map { if (it.id == updatedSideEffect.id) updatedSideEffect else it }
                    },
                    onDelete = {
                        sideEffects = sideEffects.filter { it.id != sideEffect.id }
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        SideEffectDialog(
            onDismiss = { showAddDialog = false },
            currentMedicines = newMedicines,
            currentPathologies = newPathologies,
            currentName = newName,
            currentDate = newDate,
            currentEffects = newSideEffects,
            onSave = { updatedMedicines, updatedPathologies, updatedSideEffects, updatedName, updatedDate ->
                val newSideEffect = SideEffectModele(
                    id = sideEffects.size,
                    name = updatedName,
                    date = updatedDate,
                    medicines = updatedMedicines,
                    pathologies = updatedPathologies,
                    effects = updatedSideEffects
                )
                sideEffects = sideEffects + newSideEffect
                saveSideEffects(context, sideEffects)
                showAddDialog = false
            },
            onDelete = {
                showAddDialog = false
            }
        )
    }
}


