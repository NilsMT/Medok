package com.medok.app.view.dialogView

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.*

/**
 * Fenêtre de dialog (POP-UP) permettant de confirmer la suppression d'un profil
 */
@Composable
fun DeleteProfileConfirmationDialog(navController: NavHostController) {
    var isChecked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = RedBright,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Suppression profil",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Êtes-vous sûr(e) de vouloir supprimer votre profil ? \n" +
                            "Cette action est IRRÉVERSIBLE.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.Black, thickness = 2.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it }
                    )
                    Text(
                        text = "Je confirme avoir compris les risques.",
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        confirmButton = {
            CustomButton(
                "Confirmer",
                { Toast.makeText(context, "Suppression du profil en cours...", Toast.LENGTH_SHORT).show() },
                buttonColor = RedLight,
                enabled = isChecked
            )
        },
        dismissButton = {

            CustomButton(
                "Annuler",
                { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    )
}
