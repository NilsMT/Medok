package com.medok.app.ui.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.Txt

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: Color = GreenLight,
    textColor: Color = Txt,
    fontSize: Float = 16f,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(12.dp,12.dp)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = paddings,
        modifier = modifier
            .wrapContentWidth() //equivalent to width : fit-content in CSS
            .wrapContentHeight() //equivalent to height : fit-content in CSS
            .then(Modifier.padding(4.dp)),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
