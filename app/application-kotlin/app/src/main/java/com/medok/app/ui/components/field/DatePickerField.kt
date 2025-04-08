package com.medok.app.ui.components.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.medok.app.ui.components.button.CustomButton
import java.util.Calendar

/**
 * Champ de sélection de date
 */
@Composable
fun DatePickerField(text: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    var date by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, day ->
            date = "$day/${month + 1}/$year"
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column {
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text(text) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        CustomButton(
            "Sélectionnez le jour",
            { datePickerDialog.show() },
        )
    }
}
