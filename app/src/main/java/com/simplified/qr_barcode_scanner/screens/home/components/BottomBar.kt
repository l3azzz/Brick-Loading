package com.simplified.qr_barcode_scanner.screens.home.components


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.screens.home.flipCamera // Import the flipCamera function
import kotlinx.coroutines.launch
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel


@Composable
fun BottomBar(
    navController: NavController?,
    controller: LifecycleCameraController,
    publicViewModel: PublicViewModel
) {
    Box(

        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.7f))
            .drawBehind {
                val strokeWidth = 8.dp.toPx()
                drawLine(
                    color = Color(241, 243, 244),
                    start = Offset(0f, strokeWidth / 2),
                    end = Offset(size.width, strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        val haptic = LocalHapticFeedback.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // --- Gallery Icon ---
            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                // TODO: Process the selected image URI
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                val scope = rememberCoroutineScope()
                val scale = remember { Animatable(1f) }

                Image(
                    painter = painterResource(id = R.drawable.library_white),
                    contentDescription = "Gallery Icon",
                    modifier = Modifier
                        .size(35.dp)
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                scope.launch {
                                    scale.animateTo(targetValue = 1.3f, animationSpec = tween(150))
                                    scale.animateTo(targetValue = 1f, animationSpec = tween(150))
                                }
                                galleryLauncher.launch("image/*")
                            }
                        )
                )
            }

            // --- THIS IS THE CORRECTED CAPTURE ICON ---

            val scope = rememberCoroutineScope()
            val captureScale = remember { Animatable(1f) } // Use Animatable for scale

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(90.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            // Trigger haptic feedback
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            publicViewModel.addQrData { savedQr ->
                                navController?.navigate("result_screen")
                            }

                            // Launch the animation coroutine
                            scope.launch {
                                // "shooting gun" animation: shrink fast, then expand back to normal
                                captureScale.animateTo(0.95f, tween(durationMillis = 50))
                                captureScale.animateTo(1f, tween(durationMillis = 150))
                            }
                            // TODO: Add actual image capture logic here if needed
                        }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.capture),
                    contentDescription = "Capture",
                    modifier = Modifier
                        .size(85.dp) // The base size of the image
                        .graphicsLayer {
                            // Apply the animated scale
                            scaleX = captureScale.value
                            scaleY = captureScale.value
                        }
                )
            }


            // --- Flip Camera Icon ---
            var flip_state by remember { mutableStateOf(1) } // Start with back camera (1)
            val rotation by animateFloatAsState(
                targetValue = if (flip_state == 0) 180f else 0f, // Rotate when front camera (0) is active
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
                label = "rotation"
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            // Call the function and update our state variable
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            val newCameraState = flipCamera(controller)
                            flip_state = newCameraState
                            publicViewModel.setCurrentCamera(newCameraState)

                        },
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flipcamera_white),
                    contentDescription = "Flip Camera",
                    modifier = Modifier
                        .size(35.dp) // A fixed size for the flip icon
                        .graphicsLayer(
                            rotationZ = rotation,
                        )
                )
            }
        }
    }
}
