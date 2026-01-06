package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.screens.home.flipCamera

@Composable
fun FlipCameraIcon(controller: LifecycleCameraController) {
    val interactionSource = remember { MutableInteractionSource() }

    // This state will hold the result from flipCamera(): 1 for back, 0 for front.
    // We start assuming the camera is on the back.
    var currentCam by remember { mutableStateOf(1) }
    var onFlipHold by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        // The rotation now depends on whether the camera is front-facing (0)
        targetValue = if (currentCam == 0) 180f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "rotation"
    )

    val flipSize by animateDpAsState(
        targetValue = if (onFlipHold) 30.dp else 35.dp,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
        label = "flipSize"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onFlipHold = true
                        tryAwaitRelease()
                        onFlipHold = false
                    }
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    currentCam = flipCamera(controller)
                },
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.flipcamera_white),
            contentDescription = "Flip Camera",
            modifier = Modifier
                .size(flipSize)
                .graphicsLayer(
                    rotationZ = rotation,
                )
        )
    }
}
