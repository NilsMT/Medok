package com.medok.app.view.connexion

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.medok.app.ui.components.ListInput
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.components.field.InputField
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.RedLight
import com.medok.app.ui.theme.Txt

@Composable
fun ViewProfilPageContent(navController: NavHostController) {
    val listAllergy = mutableListOf<String>(
        "Arbres","Pollen","Eau","Chat"
    )
    val map = listAllergy.associate{ el ->
        el to false
    }
    var allergyListMap = remember { mutableStateMapOf<String,Boolean>().apply {
        putAll(map)
    }}

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Votre compte",
            fontSize = 32.sp,
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
                    isReadOnly = true
                )

                InputField(label = "Nom", text = nom, onTextChange = { nom = it }, isReadOnly = true)
                InputField(label = "Prénom", text = prenom, onTextChange = { prenom = it }, isReadOnly = true)
                InputField(label = "Date de naissance", text = dateNaissance, onTextChange = { dateNaissance = it }, isReadOnly = true)

                InputField(label = "Taille (cm)", text = height, onTextChange = { height = it }, isReadOnly = true)
                InputField(label = "Poids (kg)", text = weight, onTextChange = { weight = it }, isReadOnly = true)

                ListInput(
                    "Allergies",
                    allergyListMap,
                    true
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(
            "Changer mon code de sauvegarde",
            onClick = { navController.navigate("showCode") },
            buttonColor = BlueLight
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(
            "Mettre à jour",
            { Toast.makeText(context, "Mise à jour du profil en cours...", Toast.LENGTH_SHORT).show() },
            buttonColor = BlueLight
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(
            "Modifier mon mot de passe",
            { navController.navigate("resetPassword") },
            buttonColor = RedLight
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomButton(
            "Supprimer mon compte",
            { navController.navigate("deleteProfile") },
            buttonColor = RedLight
        )
    }
}
