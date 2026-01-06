package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplified.qr_barcode_scanner.screens.home.CameraPreview

@Composable
fun ScanAreaBox() {
    Box(
        modifier = Modifier
            .padding(50.dp)
            .size(300.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(5.dp, Color.White, RoundedCornerShape(15.dp))
    ) {

    }
}


@Composable
@Preview
fun ScanAreaBoxPreview() {
    ScanAreaBox()
}