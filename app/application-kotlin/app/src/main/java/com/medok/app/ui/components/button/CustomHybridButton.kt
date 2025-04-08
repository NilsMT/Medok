package com.medok.app.ui.components.button

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.Txt

@Composable
fun CustomHybridButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    buttonColor: Color = GreenLight,
    textColor: Color = Txt,
    iconColor: Color = Txt,
    fontSize: Float = 16f,
    iconSize: DpSize = DpSize(24.dp,24.dp),
    enabled: Boolean = true,
    iconFirst: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(4.dp,4.dp)
) {
    val txtItem : @Composable () -> Unit = {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    val iconItem: @Composable () -> Unit = {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }



    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = paddings,
        modifier = modifier.then(Modifier.padding(4.dp)),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconFirst) {
                iconItem()
                Spacer(Modifier.width(8.dp))
                txtItem()

            } else {
                txtItem()
                Spacer(Modifier.width(8.dp))
                iconItem()
            }
        }

    }
}