package com.simplified.qr_barcode_scanner.screens.buymeacoffee.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplified.qr_barcode_scanner.R

@Composable
fun CoffeeButton () {
    Button(
        onClick = { /* Dummy action */ },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth(0.70f)
            .padding(horizontal = 12.dp)
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Buy us a coffee",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.coffee),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}