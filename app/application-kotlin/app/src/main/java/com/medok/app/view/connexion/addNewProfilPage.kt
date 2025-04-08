package com.medok.app.view.connexion

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.ui.theme.BlueLight
import com.medok.app.ui.theme.RedLight

@Composable
fun AddNewProfilPageContent() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConnexionBaseViewContent()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButton(
                "Retour",
                { println("Retour à l'écran précédent") },
                buttonColor = RedLight
            )

            CustomButton(
                "Créer le profil",
                onClick = {
                    Toast.makeText(context, "Création de profil en cours...", Toast.LENGTH_SHORT).show()
                },
                buttonColor = BlueLight
            )
        }
    }
}
