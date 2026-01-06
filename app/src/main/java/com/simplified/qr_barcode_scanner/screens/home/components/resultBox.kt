package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ScannedResultBox(scanData: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // fixed height, adjust as needed
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        // Heading
        Text(
            text = "Scanned Result",
            fontSize = 16.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Scrollable scanned text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // take remaining vertical space
        ) {
            val scrollState = rememberScrollState()
            Text(
                text = scanData,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .verticalScroll(scrollState)
            )
        }
    }
}
