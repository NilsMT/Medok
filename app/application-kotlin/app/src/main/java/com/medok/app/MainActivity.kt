package com.medok.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.medok.app.view.PageChargementApplication
import androidx.compose.runtime.LaunchedEffect
import com.medok.app.data.objectbox.LocalDatabaseManager


/*
 * Classe responsable de l'affichage de la page de chargement personnalisée
 * jusqu'à ce que l'activité principale (MainActivity) soit entièrement lancée.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDatabaseManager.init(this)

        // Installez l'écran de démarrage
        installSplashScreen()

        setContent {
            PageChargementApplication()

            LaunchedEffect(Unit) {
                startActivity(Intent(this@MainActivity, InitActivity::class.java))
                finish()
            }
        }
    }
}
