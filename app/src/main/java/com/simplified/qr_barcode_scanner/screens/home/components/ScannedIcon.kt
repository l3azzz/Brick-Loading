package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplified.qr_barcode_scanner.R

@Composable
fun ScannedIcon() {

    var onScannedHold by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scannedSize by animateDpAsState(
        targetValue = if (onScannedHold) 30.dp else 35.dp,
        animationSpec = tween(durationMillis = 20, easing = FastOutSlowInEasing)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(35.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onScannedHold = true
                        tryAwaitRelease()
                        onScannedHold = false

                    }
                )
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.library_white),
            contentDescription = null,
            Modifier.size(scannedSize)

        )
    }
}


@Composable
@Preview
fun ScannedIconPreview() {
    ScannedIcon()
}