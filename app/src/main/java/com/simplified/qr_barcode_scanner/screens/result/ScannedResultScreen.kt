package com.simplified.qr_barcode_scanner.screens.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar

@Composable
fun ScannedResultScreen(navController: NavController? = null, publicViewModel: PublicViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .padding(10.dp)
            .systemBarsPadding()
    ) {
        val qrData by publicViewModel.parsedContent.collectAsState()
        val qrType by publicViewModel.qrContentTypes.collectAsState()
        val scanDate by publicViewModel.scanDate.collectAsState()
        val scanTime by publicViewModel.scanTime.collectAsState()

        HeaderBar(45, "Scan Details", navController)
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Section(title = "Data: ") {

                Spacer(modifier = Modifier.height(10.dp))
                SelectionContainer {
                    Text(
                        text = qrData,
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400)
                    )
                }
            }

            Section(title = "Featured Actions") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        24.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    CircleIconButton(iconRes = R.drawable.create_icon, text = "Save") { /* TODO */ }
                    CircleIconButton(iconRes = R.drawable.share, text = "Share") { /* TODO */ }
                    CircleIconButton(
                        iconRes = R.drawable.favorite_icon,
                        text = "Favorite"
                    ) { /* TODO */ }
                }
            }

            Section(title = "More Details") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // This will show a placeholder. You can replace R.drawable.qr_code with an actual QR image painter.
                        Image(
                            painter = painterResource(id = R.drawable.unknown), // Using a placeholder
                            contentDescription = "QR Code Placeholder",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DetailRow(label = "Type:", value = qrType.firstOrNull().toString())
                        DetailRow(label = "Date:", value = scanDate)
                        DetailRow(label = "Time:", value = scanTime)
                    }
                }
            }
        }
    }
}

@Composable
private fun Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2D2D2D))
            .border(1.dp, Color(0xFF444444), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight(600),
            modifier = Modifier.padding(bottom = 0.dp)
        )
        content()
    }
}

@Composable
private fun CircleIconButton(iconRes: Int, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color(0xFF4A4A4A))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(28.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row {
        Text(
            text = label,
            color = Color.LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
