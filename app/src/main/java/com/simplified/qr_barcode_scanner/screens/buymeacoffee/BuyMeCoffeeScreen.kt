package com.simplified.qr_barcode_scanner.screens.buymeacoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar
import com.simplified.qr_barcode_scanner.screens.buymeacoffee.components.CoffeeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyMeCoffeeScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .padding(10.dp)
    )


    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            HeaderBar(45, "Buy me a coffee", navController)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.liked_icon),
                    contentDescription = "Coffee Icon",
                    modifier = Modifier
                        .size(350.dp)
                        .padding(bottom = 32.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Text(
                    text = "Like the App?",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "If you enjoy using this app, please consider supporting its development by buying me a coffee. It helps a lot!",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                CoffeeButton()

            }
        }
    }
}


@Composable
@Preview
fun BuyMeCoffeeScreenPreview() {
    BuyMeCoffeeScreen()
}