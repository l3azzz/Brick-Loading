package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplified.qr_barcode_scanner.R

@Composable
fun CaptureIcon() {
    var onCaptureHold by remember { mutableStateOf(false) }
    val captureSize by animateDpAsState(
        targetValue = if (onCaptureHold) 75.dp else 85.dp,
        animationSpec = tween(durationMillis = 20, easing = FastOutSlowInEasing)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(90.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onCaptureHold = true
                        tryAwaitRelease()
                        onCaptureHold = false

                    }
                )
            }
    ) {
        Image(
            painter = painterResource(id = if (!onCaptureHold) R.drawable.camera_white else R.drawable.camera_black),
            contentDescription = null,
            Modifier
                .size(captureSize)
                .graphicsLayer(

                )
        )
    }
}


@Composable
@Preview
fun CaptureIconPreview() {
    CaptureIcon()
}