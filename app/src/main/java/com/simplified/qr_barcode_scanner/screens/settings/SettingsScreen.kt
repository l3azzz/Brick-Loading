package com.simplified.qr_barcode_scanner.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar

@Composable
fun SettingsScreen(navController: NavController?) {

    var isDarkMode by remember { mutableStateOf(true) }
    var isFlashOn by remember { mutableStateOf(false) }
    var isEnhancedQr by remember { mutableStateOf(false) }
    var isAutoCapture by remember { mutableStateOf(false) }
    var isBeep by remember { mutableStateOf(false) }
    var isVibrate by remember { mutableStateOf(false) }
    var isAutoCopy by remember { mutableStateOf(false) }
    var isAutoOpenUrl by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .padding(10.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp)
            ) {
                item {
                    HeaderBar(45, "Settings", navController)
                }


                item { SectionTitle("APPEARANCE", modifier = Modifier.padding(top = 10.dp)) }
                item {
                    ColorSettingRow()
                }
                item {
                    ToggleSettingRow(
                        title = "Dark Mode",
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { SectionTitle("CAMERA") }
                item {
                    SelectionSettingRow(
                        title = "Primary Camera",
                        selectedValue = "mx",
                        onClick = { /* TODO */ }
                    )
                }
                item {
                    SelectionSettingRow(
                        title = "Secondary Camera",
                        selectedValue = "mx",
                        onClick = { /* TODO */ }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Turn on flash on startup",
                        checked = isFlashOn,
                        onCheckedChange = { isFlashOn = it }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Enable enhanced scanning",
                        checked = isEnhancedQr,
                        onCheckedChange = { isEnhancedQr = it }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Enable autocapture",
                        checked = isAutoCapture,
                        onCheckedChange = { isAutoCapture = it }
                    )
                }


                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { SectionTitle("PREFERENCES") }
                item {
                    ActionSettingRow(title = "Home preference") { /* TODO */ }
                }
                item {
                    ToggleSettingRow(
                        title = "Beep",
                        checked = isBeep,
                        onCheckedChange = { isBeep = it }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Vibrate",
                        checked = isVibrate,
                        onCheckedChange = { isVibrate = it }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Auto Copy",
                        checked = isAutoCopy,
                        onCheckedChange = { isAutoCopy = it }
                    )
                }
                item {
                    ToggleSettingRow(
                        title = "Auto open URL",
                        checked = isAutoOpenUrl,
                        onCheckedChange = { isAutoOpenUrl = it }
                    )
                }
                item {
                    ActionSettingRow(title = "Custom action") { /* TODO */ }
                }
                item {
                    SelectionSettingRow(
                        title = "Use browser",
                        selectedValue = "Chrome",
                        onClick = { /* TODO */ }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { SectionTitle("DATA") }
                val darkishRed = Color(255, 92, 52)
                item {
                    ActionSettingRow(title = "Clear History", color = darkishRed) { /* TODO */ }
                }
                item {
                    ActionSettingRow(title = "Clear Saved", color = darkishRed) { /* TODO */ }
                }
                item {
                    ActionSettingRow(title = "Clear favorites", color = darkishRed) { /* TODO */ }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
                item {
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.2f))
                    ) {
                        Text("Delete App Data", color = Color.White)
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Credits", color = Color.Gray, fontSize = 12.sp)
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }

    }

}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        color = Color.Gray,
        fontWeight = FontWeight(900),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        fontSize = 14.sp
    )
}

@Composable
fun ColorSettingRow() {
    val themeColors = listOf(
        Color(0xFFD32F2F),
        Color(0xFF388E3C),
        Color(0xFF0288D1),
        Color(0xFFFBC02D),
        Color(0xFF8E24AA),
        Color(0xFFE64A19)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text("Theme color", color = Color.White, fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            themeColors.forEach { color ->
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp)
                        .background(color, shape = MaterialTheme.shapes.small)
                )
            }
            Icon(
                painter = painterResource(R.drawable.colorize),
                contentDescription = "Color Picker",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ToggleSettingRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        onClick = { onCheckedChange(!checked) },
        shape = RoundedCornerShape(50),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = Color.White, fontSize = 16.sp)
            Switch(
                checked = checked,
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF4CAF50),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray,
                )
            )
        }
    }
}

@Composable
fun SelectionSettingRow(title: String, selectedValue: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = Color.White, fontSize = 16.sp)
            Text(selectedValue, color = Color.Gray, fontSize = 16.sp)
        }
    }
}

@Composable
fun ActionSettingRow(title: String, color: Color = Color.White, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Text(
                text = title,
                color = color,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen(null)
}
