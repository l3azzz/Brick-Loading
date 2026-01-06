package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.flashOnClick() = composed {

    val scope = rememberCoroutineScope()
    var clicked by remember { mutableStateOf(false) }

    val bgColor = if (clicked) Color.White else Color.Black.copy(alpha = 0.5f)

    this
        .background(bgColor, RoundedCornerShape(30.dp))
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            // Launch inside coroutine scope
            scope.launch {
                clicked = true
                delay(50) // 50ms flash
                clicked = false
            }
        }
}