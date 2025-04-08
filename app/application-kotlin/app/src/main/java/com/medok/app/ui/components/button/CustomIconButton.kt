package com.medok.app.ui.components.button

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.medok.app.ui.theme.GreenLight
import com.medok.app.ui.theme.Txt

@Composable
fun CustomIconButton(
    icon: Painter,
    onClick: () -> Unit,
    buttonColor: Color = GreenLight,
    iconColor: Color = Txt,
    iconSize: DpSize = DpSize(24.dp,24.dp),
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(4.dp,4.dp)
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = paddings,
        modifier = modifier.then(Modifier.padding(4.dp)),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row() {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}