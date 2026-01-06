package com.simplified.qr_barcode_scanner.navigation.HeaderBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.components.BackIcon

@Composable
fun HeaderBar( headerSize: Int = 45, headerName: String = "", navController: NavController? = null,) {




    Box(
        modifier = Modifier
            .fillMaxWidth() // No .align() needed here for the container itself
            .padding(7.dp)
    ) {

        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            BackIcon(navController , size = headerSize)
        }

        Text(
            text = headerName,
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun HeaderBarPreview() {
    HeaderBar(45)
}