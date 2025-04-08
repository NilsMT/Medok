package com.medok.app.view.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.medok.app.ui.components.button.CustomButton

@Composable
fun SearchAndNewReminder(
    text: String,
    onSearchChange: (String) -> Unit,
    onNewReminder: () -> Unit
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
            onClick = { onNewReminder() },
        )
    }
}

