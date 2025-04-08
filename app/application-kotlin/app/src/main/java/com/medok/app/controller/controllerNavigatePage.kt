package com.medok.app.controller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.medok.app.view.reminders.MedicalRemindersContent
import com.medok.app.view.sideeffects.SideEffectsJournalContent
import com.medok.app.view.connexion.ViewProfilPageContent
import com.medok.app.view.dialogView.DeleteProfileConfirmationDialog
import com.medok.app.view.dialogView.PasswordResetDialog
import com.medok.app.view.dialogView.RememberBackupCodeDialog
import com.medok.app.view.prescription.PrescriptionPage


//Permet d'afficher un message visuel sur l'application
@Composable
fun AfficherMessageVisuel(message : String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Composable
fun AppNavHost(navController: NavHostController) {

    //startDestination appelle la page située dans "rappels" dès le lancement de l'application
    NavHost(navController, startDestination = "rappels") {

        //FOOTER
        composable("rappels") { MedicalRemindersContent(navController) }
        composable("ordonnances") { PrescriptionPage(navController) }
        composable("effets") { SideEffectsJournalContent(navController) }
        composable("profils") { ViewProfilPageContent(navController) }
        composable("contacts") { AfficherMessageVisuel("Cette page n'a pas encore été implémentée") }


        //PROFIL
        composable("showCode"){ RememberBackupCodeDialog(navController)}
        composable("resetPassword"){ PasswordResetDialog(navController) }
        composable("deleteProfile"){ DeleteProfileConfirmationDialog(navController) }



    }
}

//Si vous souhaitez mettre des controleurs dans les vues alors mettre ceci dans les paramètres : navController