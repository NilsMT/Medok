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
import androidx.compose.runtime.mutableStateListOf
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
import com.medok.app.view.list.ListDialogUnique

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ListInputUnique(
    inputName: String,
    selectedItem: String,
    selectionList: MutableList<String>,
    isMutable: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    val selectionListState = remember {
        mutableStateListOf<String>().apply {
            addAll(selectionList)
        }
    }

    val selectedState = remember {
        mutableStateOf(selectedItem)
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectedState.value,

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
        ListDialogUnique(
            selectedState.value,
            selectionListState,
            isMutable,
            onOk = { selected, list ->
                // Update selectionState and sync with selectionMap
                selectionListState.clear()
                selectionListState.addAll(list)

                // Reflect changes in selectionMap, ensuring deletions are respected
                selectedState.value = selected

                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}