package org.nibienvenu.school.presentation.composents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VisibilityIcon(visible: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(24.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Dessiner l'œil (commun aux deux états)
        drawOval(
            color = Color.DarkGray,
            size = Size(canvasWidth, canvasHeight * 0.5f),
            topLeft = Offset(0f, canvasHeight * 0.25f)
        )

        // Dessiner la pupille
        drawCircle(
            color = Color.White,
            radius = canvasWidth * 0.25f,
            center = Offset(canvasWidth * 0.5f, canvasHeight * 0.5f)
        )

        // Dessiner la barre diagonale si l'œil est fermé
        if (!visible) {
            drawLine(
                color = Color.Red,
                start = Offset(0f, 0f),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 3.dp.toPx()
            )
        }
    }
}