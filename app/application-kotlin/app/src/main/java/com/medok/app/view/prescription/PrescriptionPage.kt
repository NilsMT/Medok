package com.medok.app.view.prescription

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.medok.app.data.objectbox.LocalDatabaseManager
import com.medok.app.model.Medicine
import com.medok.app.model.prescription.PrescribedMedicine
import com.medok.app.model.prescription.SimplePrescription
import com.medok.app.ui.theme.Txt

@Composable
fun PrescriptionPage(navController: NavHostController) {
    val localDB = LocalDatabaseManager
    val prescriptionList = localDB.getPrescriptionBox().all

    val context = LocalContext.current

    val prescriptionListState = remember {
        mutableStateListOf<SimplePrescription>().apply {
            addAll(prescriptionList)
        }
    }

    var searchQuery by remember { mutableStateOf("") }

    var showedDialog by remember { mutableStateOf<ShowedDialog>(ShowedDialog.NONE) }

    var targettedItem by remember { mutableStateOf<SimplePrescription>( //a placeholder that will always change dont worry
        SimplePrescription(
            doctorName = "", doctorPhoneNumber = "", address = "", redactionDate = ""
        )
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ordonnances Médicales",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Txt,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        //search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Rechercher") },
            trailingIcon = {
                Icon(
                    contentDescription = "Icône de recherche",
                    imageVector = Icons.Default.Search
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OCRScanner(
            onShowDialog = { item ->
                targettedItem = item
                showedDialog = ShowedDialog.CREATION
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()) //make only the list scrollable
                .fillMaxWidth()
        ) {
            prescriptionListState.filter { //filter the list to match search query
                it.toString().contains(searchQuery, ignoreCase = true)
            }.forEach { prescription ->
                PrescriptionItem(
                    prescription,
                    onShowDialog = { item ->
                        targettedItem = item
                        showedDialog = ShowedDialog.MODIFICATION

                    },
                    onCreateReminder = { item ->
                        showedDialog = ShowedDialog.NONE

                        Toast.makeText(
                            context,
                            "Le rappel pour l'ordonnance ${item.id} a été créé avec succès !",
                            Toast.LENGTH_SHORT
                        ).show()

                        Toast.makeText(
                            context,
                            "En vrai c'est un mensonge, mais ça arrive bientôt",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    if (showedDialog == ShowedDialog.CREATION) {
        PrescriptionDialog(
            true,
            targettedItem,
            onOk = { item ->
                prescriptionListState.add(item)
                showedDialog = ShowedDialog.NONE
            },
            onDismiss = {
                localDB.deletePrescription(targettedItem.id)
                showedDialog = ShowedDialog.NONE
            }
        )
    } else if (showedDialog == ShowedDialog.MODIFICATION){
        PrescriptionDialog(
            false,
            targettedItem,
            onOk = { item ->
                //edit existing item
                localDB.addPrescription(item)
                prescriptionListState.clear()
                prescriptionListState.addAll(localDB.getPrescriptionBox().all)
                showedDialog = ShowedDialog.NONE
            },
            onDismiss = {
                showedDialog = ShowedDialog.NONE
            },
            onDelete = { item ->
                prescriptionListState.removeIf {
                    it.id == item.id
                }

                showedDialog = ShowedDialog.NONE
            }
        )
    }
}

enum class ShowedDialog() {
    NONE,
    CREATION,
    MODIFICATION
}