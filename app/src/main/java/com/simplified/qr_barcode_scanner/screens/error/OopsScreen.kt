package com.simplified.qr_barcode_scanner.screens.about

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar
import com.simplified.qr_barcode_scanner.screens.error.components.BackButton

@Composable
fun OopsScreen(
    modifier: Modifier = Modifier
        .background(Color(27, 26, 25))
) {
    val size = 45
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(10.dp)
    ) {
        HeaderBar(45, "")
        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(50.dp)
                    .size(250.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(5.dp, Color.White, RoundedCornerShape(15.dp))
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxSize()
            ) {
            }
            Text(
                text = "Oops...",
                color = Color.White,
                fontSize = 60.sp,
                fontWeight = FontWeight(900),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            Text(
                text = "This Barcode/Image Seems to Provide No Data",
                color = Color.Gray,
                fontSize = 22.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 7.5.dp, end = 7.5.dp)

            )
            BackButton(45)
        }
    }
}






@Preview(

    device = "spec:width=1080px,height=2424px", showSystemUi = true
)
@Composable
fun OopsScreenPreview() {
    OopsScreen()
}