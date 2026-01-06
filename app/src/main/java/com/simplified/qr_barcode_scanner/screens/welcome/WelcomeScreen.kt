package com.simplified.qr_barcode_scanner.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplified.qr_barcode_scanner.R

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .systemBarsPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Logo / Image
        Image(
            painter = painterResource(R.drawable.hi),
            contentDescription = null,
            modifier = Modifier.size(260.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Main Title
        Text(
            text = "Welcome",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // App Name
        Text(
            text = "To QR Code Scanner",
            color = Color(0xFFBDBDBD),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Accent Divider
        Spacer(
            modifier = Modifier
                .height(3.dp)
                .width(60.dp)
                .background(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(50)
                )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Description Text
        Text(
            text = "Scan, create, and manage QR codes\nfast, simple, and secure.",
            color = Color(0xFF9E9E9E),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Bottom Hint
        Text(
            text = "Getting things ready…",
            color = Color(0xFF757575),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}
//
//
//@Composable
//@Preview
//fun WelcomeScreenPreview() {
//    WelcomeScreen()
//}