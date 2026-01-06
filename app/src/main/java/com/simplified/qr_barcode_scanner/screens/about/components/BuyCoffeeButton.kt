package com.simplified.qr_barcode_scanner.screens.about.components

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CoffeeButton(
    size: Int = 45,
    text: String = "Undefined",
    navController: NavController? = null
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    navController?.navigate("buymecoffee_screen")
                }
            )
            .padding((size * 0.3f).dp)
            .height(size.dp)
            .wrapContentWidth()
            .drawBehind {
                val paint = Paint().apply {
                    color = Color.White.toArgb()
                    style = Paint.Style.STROKE
                    strokeWidth = (size * 0.047f).dp.toPx()
                    maskFilter =
                        BlurMaskFilter((size * 0.223f).dp.toPx(), BlurMaskFilter.Blur.NORMAL)
                }
                val cornerRadius = (size / 2).dp.toPx()
                drawContext.canvas.nativeCanvas.drawRoundRect(
                    0f, 0f,
                    this.size.width,
                    this.size.height,
                    cornerRadius,
                    cornerRadius,
                    paint
                )

            }
            .clip(RoundedCornerShape((size / 2).dp))
            .background(Color.White)
            .padding(horizontal = (size * 0.444f).dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.coffee),
//                contentDescription = null,
//                modifier = Modifier.size((size * 0.667f).dp)
//            )
//            Spacer(modifier = Modifier.width((size * 0.178f).dp))
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = (size * 0.311f).sp
            )
        }
    }
}

@Composable
@Preview
fun CoffeeButtonPreview() {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .padding(20.dp)
    ) {
        CoffeeButton()
    }
}
