package com.simplified.qr_barcode_scanner.screens.settings.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ToggleButton(
    checked: Boolean = false,
    onToggle: (Boolean) -> Unit = {}
) {
    var isEnabled by remember { mutableStateOf(checked) }
    Switch(
        checked = isEnabled,
        onCheckedChange = {
            isEnabled = it
            onToggle(it)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Gray,
            checkedTrackColor = Color.White,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = Color.Gray,
        )
    )
}


@Composable
@Preview
fun ToggleButtonPreview() {
    ToggleButton()
}
