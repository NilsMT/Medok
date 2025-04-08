package com.medok.app.view.sideeffects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.medok.app.ui.components.button.CustomButton


@Composable
fun SearchAndNewSideEffect(
    text: String,
    onSearchChange: (String) -> Unit,
    onNewSideEffect: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchChange(searchText)
            },
            placeholder = { Text("Rechercher") },
            trailingIcon = {
                Icon(
                    contentDescription = "Icône de recherche",
                    imageVector = Icons.Default.Search
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        CustomButton(
            text,
            onClick = { onNewSideEffect() },
        )
    }
}