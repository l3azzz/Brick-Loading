package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.screens.home.setFlash
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel


@Composable
fun TopBar(
    navController: NavController? = null,
    onMenuClick: () -> Unit,
    controller: LifecycleCameraController,
    publicViewModel: PublicViewModel
) {
    val currentCamera by publicViewModel.currentCamera.collectAsState()


    val interactionSource = remember { MutableInteractionSource() }

    var isFlashOn by remember { mutableStateOf(false) }

    var isFrontCamera = if (currentCamera == 0) true else false


    LaunchedEffect(controller.cameraSelector) {
        val currentlyFront = if (currentCamera == 0) true else false

        if (currentlyFront) {
            isFlashOn = false
            setFlash(controller, 0)
        }
    }

    val flashIcon = when {
        isFrontCamera -> R.drawable.flash_dim
        isFlashOn -> R.drawable.flash_on
        !isFlashOn -> R.drawable.flash_off
        else -> R.drawable.flash_off
    }

    // Determine the alpha (opacity) for the flash icon.
    val flashAlpha = if (isFrontCamera) 0.5f else 1.0f

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(20.dp)
    ) {

        // Menu
        Box {
            Image(
                painter = painterResource(R.drawable.menu_white),
                contentDescription = "Menu",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onMenuClick
                    )
            )
        }
//        Text(
//            text = if (currentCamera == 0) "Front Camera" else "Back Camera"
//        )


        // --- Corrected Flash Toggle ---
        Box {
            Image(
                painter = painterResource(flashIcon),
                contentDescription = "Toggle Flash",
                modifier = Modifier
                    .size(38.dp)
                    .alpha(flashAlpha) // Apply dimming effect
                    .clickable(
                        // This 'enabled' state is now guaranteed to be correct on every flip.
//                        enabled = if (!isFrontCamera) true else false,

                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        // This code only runs if it's the back camera.
                        isFlashOn = !isFlashOn
                        val newState = if (isFlashOn) 1 else 0
                        setFlash(controller, newState)
                    }
            )
        }
    }
}
