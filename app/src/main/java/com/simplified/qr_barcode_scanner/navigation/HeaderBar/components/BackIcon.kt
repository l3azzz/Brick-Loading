package com.simplified.qr_barcode_scanner.navigation.HeaderBar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R

@Composable
fun BackIcon(navController: NavController? = null, size: Int = 45) {
    Box(
        modifier = Modifier
            .size(size.dp)
//            .drawBehind {
//                val paint = Paint().apply {
//                    color = Color.White.toArgb()
//                    style = Paint.Style.STROKE
//                    strokeWidth = 1.2.dp.toPx()
//                    maskFilter = BlurMaskFilter(6.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
//                }
//                drawContext.canvas.nativeCanvas.drawCircle(
//                    center.x,
//                    center.y,
//                    size.width / 2,
//                    paint
//                )
//            }
            .clip(CircleShape)
            .border((size * 0.069).dp, Color.White, CircleShape)

    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back Button",
            modifier = Modifier
                .size((size * 0.89f).dp)
                .align(Alignment.Center)
                .clickable(
                    onClick = {
                        navController?.popBackStack()
                    }
                )

        )
    }
}

@Preview
@Composable
fun BackIconPreview() {
    BackIcon()
}
