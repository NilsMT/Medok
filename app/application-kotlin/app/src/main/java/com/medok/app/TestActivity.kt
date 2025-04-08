package com.medok.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.medok.app.model.Doctor
import com.medok.app.model.EmailAddress
import com.medok.app.model.Organization
import com.medok.app.model.PhoneNumber
import com.medok.app.ui.components.ListInput

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoOrdonnances()
        }
    }


    @Composable
    fun DemoOrdonnances() {

    }

    @Composable
    fun TestDocAndOrg() {

        /////////////////

        val orgList = mutableListOf(
            Organization(
                "Cabinet du doc",
                "1 rue d'ici",
            ),
            Organization(
                "Cabinet du doc",
                "1 rue d'ici",
                "0",
                123,

            )
        )

        val docList = mutableListOf(
            Doctor(
                "Bezos",
                "Jeff",
                PhoneNumber(33,"0769437638"),
                EmailAddress("jeff.bezos@amazon.com"),
                orgList[0],
                0
            ),
            Doctor(
                "Dahmer",
                "Jeffrey",
                organization = orgList[1],
                rpps = 0
            )
        )

        /////////////////

        val docMap = docList.associate {
            it.toString() to false
        }.toMutableMap()

        val orgMap = orgList.associate {
            it.toString() to false
        }.toMutableMap()

        Column {
            ListInput(
                "Docteur",
                docMap,
                false
            )

            ListInput(
                "Organisations",
                orgMap,
                true
            )
        }
    }
}