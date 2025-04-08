package com.medok.app.view.dialogView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.field.PasswordInputField
import com.medok.app.ui.theme.GreenLight


/**
 * Fenêtre de dialog (POP-UP) permettant de modifier son mot de passe.
 */
@Composable
fun PasswordResetDialog(navController: NavHostController) {
    var backupCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isFormValid =
        backupCode.isNotBlank() && newPassword.isNotBlank() && confirmPassword.isNotBlank()
    val doPasswordsMatch = newPassword == confirmPassword
    val passwordValidationMessage = validatePassword(newPassword)

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "Réinitialiser votre mot de passe",
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
                HorizontalDivider(color = Color.Black, thickness = 2.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Veuillez entrer votre code de sauvegarde, un nouveau mot de passe, et confirmer ce dernier.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = backupCode,
                    onValueChange = { backupCode = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = { Text("Code de sauvegarde") },
                    maxLines = 1,
                    singleLine = true,
                    isError = backupCode.isBlank()
                )

                PasswordInputField(
                    label = "Nouveau mot de passe",
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    errorMessage = passwordValidationMessage,
                    isPasswordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                )


                Spacer(modifier = Modifier.height(12.dp))

                PasswordInputField(
                    label = "Confirmer mot de passe",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    errorMessage = if (!doPasswordsMatch && confirmPassword.isNotEmpty()) "Les mots de passe ne correspondent pas." else "",
                    isPasswordVisible = confirmPasswordVisible,
                    onPasswordVisibilityChange = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        },
        confirmButton = {
            CustomButton(
                "Confirmer",
                onClick = { },
                enabled = isFormValid && doPasswordsMatch && passwordValidationMessage.isEmpty(),
                buttonColor = GreenLight
            )
        },
        dismissButton = {
            CustomButton(
                "Annuler",
                { navController.popBackStack() }
            )
        }

    )
}





/**
 * Cette fonction vérifie si un mot de passe rentre dans les normes imposés par les développeurs (longueur + caractère spécial)
 * @param password le mot de passe rentré par l'utilisateur
 * @return Rien si le mot de passe rentre dans les normes ou un message d'erreur le cas inverse
 */
fun validatePassword(password: String): String {
    val specialCharPattern = "[!@#\$%^&*()\\-_=+{};:,<.>\\[\\]\\\\]".toRegex()
    return when {
        password.length in 0..7 -> "Le mot de passe doit comporter au moins 8 caractères."
        !specialCharPattern.containsMatchIn(password) -> "Le mot de passe doit comporter au moins un caractère spécial."
        else -> ""
    }
}