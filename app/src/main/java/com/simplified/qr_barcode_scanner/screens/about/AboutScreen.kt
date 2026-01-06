package com.simplified.qr_barcode_scanner.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.screens.about.components.CoffeeButton
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar

@Composable
fun AboutScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .systemBarsPadding()
            .padding(10.dp)
    ) {
        HeaderBar(45, "About", navController)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .clip(RoundedCornerShape(15.dp))
//                    .padding(bottom = (-30).dp)
//                    .border(5.dp, Color.White, RoundedCornerShape(15.dp))
            ) {
//                Image(
//                    painter = painterResource(R.drawable.icon_full),
//                    contentDescription = "logo",
//                    modifier = Modifier.fillMaxSize()
//                )
                Image(
                    painter = painterResource(R.drawable.undefined_white),
                    contentDescription = "logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height((-12).dp))

            Text(
                text = "We Are Simplified!",
                color = Color.White,
                fontSize = 60.sp,
                lineHeight = 55.sp,
                fontWeight = FontWeight(900),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "We Make Apps that Counts on You",
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(90.dp))

            Text(
                text = "Without Ads And Subscription We Just Need You To Support Us",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(5.dp))

            CoffeeButton(55, "Buy Us A Coffee", navController)
        }
    }
}


@Preview(
    device = "spec:width=1080px,height=2424px", showSystemUi = true
)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
