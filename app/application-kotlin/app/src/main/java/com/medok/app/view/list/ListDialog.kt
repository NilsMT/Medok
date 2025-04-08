package com.medok.app.view.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Deselect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.button.CustomIconButton
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt

/**
 * Fenêtre de dialog (POP-UP) permettant de consulter, cocher et modifier des éléments d'une liste
 * @param selectionState liste des éléments associés à des booléens
 * @param isMutable booléen indiquant si la liste est modifiable (suppression & ajout)
 * @param onOk action à effectuer avec la liste retournée à la fin du dialog (quand l'utilisateur appuis sur Ok)
 * @param onDismiss action à faire si le dialog est annulé (quand l'utilisateur appuis sur Annuler / sur les bords)
 */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ListDialog(
    selectionState: MutableMap<String, Boolean>,
    isMutable: Boolean,
    onOk: (MutableMap<String, Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }

    val bufferState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            putAll(selectionState) //link all items of the buffer to the selectionState
        }
    }

    var selectedCount by remember {
        mutableStateOf(
            bufferState.filter { it.value }.size
        )
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sélectionner un ou des éléments") },


        text = {
            Column {
                //search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Rechercher") },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Txt,
                        unfocusedBorderColor = Txt,
                        focusedLabelColor = Txt,
                        unfocusedLabelColor = Txt,
                    )
                )

                Spacer(Modifier.height(8.dp))

                //scrollable list of items
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()) //make only the list scrollable
                        .fillMaxWidth()
                ) {
                    bufferState.filter { //filter the list to match search query
                        it.key.contains(searchQuery, ignoreCase = true)
                    }.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f) //allow content to share space
                                ) {
                                    Checkbox(
                                        checked = bufferState[item.key] ?: false,
                                        onCheckedChange = { isChecked ->
                                            bufferState[item.key] = isChecked
                                            selectedCount += if (isChecked) 1 else -1
                                        }
                                    )
                                    Text(
                                        text = item.key,
                                        //handle overflowing
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .weight(1f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                if (isMutable) {
                                    CustomIconButton(
                                        icon = rememberVectorPainter(Icons.Default.DeleteForever),
                                        onClick = { bufferState.remove(item.key) },
                                        buttonColor = RedLight,
                                    )
                                }
                            }

                            if (isMutable) {
                                CustomIconButton(
                                    icon = rememberVectorPainter(Icons.Default.DeleteForever),
                                    onClick = { bufferState.remove(item.key) },
                                    buttonColor = RedLight,
                                )
                            }
                        }
                    }

                    if (
                        bufferState.filter {
                            it.key.contains(searchQuery, ignoreCase = true)
                        }.isEmpty()) {
                        Box {
                            Text(
                                text = "Il n'y a rien qui correspond à votre recherche :(",
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }
        },


        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomIconButton(
                    icon = rememberVectorPainter(Icons.Default.Deselect),
                    onClick = {
                        bufferState.forEach {
                            bufferState[it.key] = false
                        }
                        selectedCount = 0
                    },
                    buttonColor = BlueLight,
                    enabled = selectedCount > 0
                )

                if (isMutable) {
                    CustomIconButton(
                        icon = rememberVectorPainter(Icons.Default.Add),
                        onClick = { showDialog = true },
                        buttonColor = GreenLight,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    text = "Annuler",
                    onClick = onDismiss,
                    buttonColor = RedLight
                )

                CustomButton(
                    text = "Ok",
                    onClick = { onOk(bufferState) },
                    buttonColor = GreenLight
                )
            }
        }
    )

    if (showDialog) {
        ListAddDialog(
            onOk = {
                bufferState[it] = false
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            },
            bufferState
        )
    }
}