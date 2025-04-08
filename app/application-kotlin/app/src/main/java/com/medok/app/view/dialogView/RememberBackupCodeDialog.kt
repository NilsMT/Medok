package com.medok.app.view.dialogView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.navigation.NavHostController
import com.medok.app.controller.CodeController
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.Txt

/**
 * Fenêtre de dialog (POP-UP) permettant d'afficher pour la 1ère fois le code de sauvegarde
 */
@Composable
fun RememberBackupCodeDialog(navController: NavHostController) {
    val codeController = CodeController()
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "Votre code de sauvegarde",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                HorizontalDivider(color = Color.Black, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFFF5F5F5), // Gris clair
                    shadowElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = codeController.generateRandomCode(),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            color = Txt,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ce code vous permet de changer votre mot de passe si vous l’oubliez. \n" +
                            "Nous vous recommandons fortement de :",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "- Noter ce code sur un support sécurisé\n" +
                            "- Ne pas le partager avec des tiers",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.Black, thickness = 1.dp)
            }
        },
        confirmButton = {
            Button(
                onClick = { navController.popBackStack() }, //TODO : implémenter le système permettant de save le new code
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenLight),
            ) {
                Text(
                    "J'ai sauvegardé mon code",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            CustomButton(
                "J'ai sauvegardé mon code",
                onClick = { navController.popBackStack() }, //TODO : implémenter le système permettant de save le new code
            )
        },
    )
}

