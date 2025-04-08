package com.medok.app.view.connexion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.ui.components.ListInput
import com.medok.app.ui.components.field.InputField
import com.medok.app.ui.components.field.PasswordInputField
import com.medok.app.ui.components.field.DatePickerField
import com.medok.app.ui.theme.Txt
import com.medok.app.view.dialogView.validatePassword

class ConnexionBaseView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                ConnexionBaseViewContent()
            }
        }
    }
}

@Composable
fun ConnexionBaseViewContent() {
    val listAllergy = mutableListOf<String>(
        "Arbres","Pollen","Eau","Chat"
    )
    val map = listAllergy.associate{ el ->
        el to false
    }
    var allergyListMap = remember { mutableStateMapOf<String,Boolean>().apply {
        putAll(map)
    }}

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val passwordValidationMessage = validatePassword(password)

    val isFormValid =
        nom.isNotBlank() && prenom.isNotBlank() && id.isNotBlank() && dateNaissance.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && allergy.isNotBlank() && height.isNotBlank() && weight.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Créer votre compte",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Txt,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InputField(
                    label = "Identifiant",
                    text = id,
                    onTextChange = { id = it },
                )
                PasswordInputField(
                    label = "Mot de passe",
                    value = password,
                    onValueChange = { password = it },
                    errorMessage = passwordValidationMessage,
                    isPasswordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                )
                PasswordInputField(
                    label = "Confirmer le mot de passe",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    errorMessage = passwordValidationMessage,
                    isPasswordVisible = confirmPasswordVisible,
                    onPasswordVisibilityChange = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    },
                )
                InputField(label = "Nom", text = nom, onTextChange = { nom = it })
                InputField(label = "Prénom", text = prenom, onTextChange = { prenom = it })
                DatePickerField(
                    text = "Date de naissance",
                    onDateSelected = { dateNaissance = it }
                )
                InputField(label = "Taille (cm)", text = height, onTextChange = { height = it })
                InputField(label = "Poids (kg)", text = weight, onTextChange = { weight = it })

                ListInput(
                    "Allergies",
                    allergyListMap,
                    true,
                )
            }
        }
    }
}