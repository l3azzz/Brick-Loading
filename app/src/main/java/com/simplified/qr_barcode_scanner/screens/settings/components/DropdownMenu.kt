package com.simplified.qr_barcode_scanner.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropDownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = selectedOption,
            modifier = Modifier
                .clickable { isExpanded = true }
                .padding(16.dp),
            color = Color.Gray
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        isExpanded = false
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun DropDownMenuPreview() {
    val cameraOptions = remember { listOf("12x", "48x", "83x") }
    var selectedCamera by remember { mutableStateOf(cameraOptions[0]) }

    DropDownMenu(
        options = cameraOptions,
        selectedOption = selectedCamera,
        onOptionSelected = { selectedCamera = it }
    )
}