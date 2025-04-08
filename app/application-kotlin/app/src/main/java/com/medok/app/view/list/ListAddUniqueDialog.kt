package com.medok.app.view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt

/**
 * Fenêtre de dialog (POP-UP) permettant d'ajouter un élément à la liste
 */
@Composable
fun ListAddDialog(
    onOk: (String) -> Unit,
    onDismiss: () -> Unit,
    buffer: MutableList<String>
) {
    var newItemText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajoutez un élément") },
        text = {
            Column {
                val isError = newItemText.isEmpty() || buffer.contains(newItemText)

                OutlinedTextField(
                    value = newItemText,
                    onValueChange = {
                        newItemText = it.lowercase().replaceFirstChar {
                            it.uppercaseChar()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nom de l'élément") },

                    //style according to being error-free
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isError) colorScheme.error else Txt,
                        unfocusedBorderColor = if (isError) colorScheme.error else Txt,
                        focusedLabelColor = if (isError) colorScheme.error else Txt,
                        unfocusedLabelColor = if (isError) colorScheme.error else Txt,
                    ),

                    supportingText = {
                        if (isError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = when {
                                    newItemText.isEmpty() -> "Le texte ne peut être vide"
                                    buffer.contains(newItemText) -> "Cet élément existe déjà"
                                    else -> ""
                                },
                                color = colorScheme.error
                            )
                        }
                    }
                )
            }
        },
        confirmButton = {
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
                    onClick = { onOk(newItemText) },
                    buttonColor = GreenLight,
                    enabled = newItemText.isNotEmpty() && !(buffer.contains(newItemText))
                )
            }
        }
    )
}