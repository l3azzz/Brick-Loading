package com.simplified.qr_barcode_scanner.screens.error.components

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplified.qr_barcode_scanner.R

@Composable
fun BackButton(size: Int) {
    val size = size
    Box(
        modifier = Modifier
            .padding(top = 40.dp, bottom = 20.dp)
            .height(size.dp)
            .wrapContentWidth()
            .drawBehind {
                val paint = Paint().apply {
                    color = Color(80, 80, 80).toArgb()
                    style = Paint.Style.STROKE
                    strokeWidth = (size * 0.047f).dp.toPx()
                    maskFilter = BlurMaskFilter((size * 0.223f).dp.toPx(), BlurMaskFilter.Blur.NORMAL)
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
            .background(Color(48, 48, 48))
            .padding(horizontal = (size * 0.5f).dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier.size((size * 0.4f).dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width((size * 0.178f).dp))
            Text(
                text = "Go Back",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize =  (size * 0.371f).sp
            )
        }
    }
}