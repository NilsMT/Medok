package com.medok.app.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.ui.components.button.CustomButton
import com.medok.app.view.list.ListDialog
import com.medok.app.ui.theme.*

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ListInput(
    inputName: String,
    selectionMap: MutableMap<String, Boolean>,
    isMutable: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    val selectionState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            putAll(selectionMap)
        }
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectionState.filter {
                it.value
            }.keys.joinToString(", "),

            //fake textField style

            singleLine = true,
            maxLines = 1,
            isError = false,
            onValueChange = {}, //ensure readOnly (just in case)
            readOnly = true, //readOnly
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledLabelColor = Txt,
                disabledTextColor = Txt,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = Txt,
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Txt,
                lineHeight = TextUnit.Unspecified,
                textDecoration = TextDecoration.None
            ),
            label = {
                Text(inputName)
            }
        )

        CustomButton(
            text = "Voir la liste",
            onClick = { showDialog = true }
        )
    }

    if (showDialog) {
        ListDialog(
            selectionState,
            isMutable,
            onOk = {
                // Update selectionState and sync with selectionMap
                selectionState.clear()
                selectionState.putAll(it)

                // Reflect changes in selectionMap, ensuring deletions are respected
                selectionMap.clear()
                selectionMap.putAll(selectionState)


                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}