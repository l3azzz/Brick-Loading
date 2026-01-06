package com.simplified.qr_barcode_scanner.screens.home

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.LifecycleCameraController

/**
 * Flips the camera between the front and back lenses. This is the robust way to do it.
 *
 * @param controller The current LifecycleCameraController instance.
 * @return Returns `1` if the camera is now facing the back, or `0` if it is facing the front.
 */
fun flipCamera(controller: LifecycleCameraController): Int {
    val currentLensFacing =
        controller.cameraSelector.lensFacing ?: CameraSelector.LENS_FACING_BACK

    val CAMERA_FRONT = 0
    val CAMERA_BACK = 1

    return if (currentLensFacing == CameraSelector.LENS_FACING_BACK) {
        controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        CAMERA_FRONT
    } else {
        controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        CAMERA_BACK
    }

}

/**
 * Sets the camera's flashlight (torch) state directly.
 *
 * @param controller The current LifecycleCameraController instance.
 * @param state The desired state: `1` for ON, `0` for OFF.
 */
fun setFlash(controller: LifecycleCameraController, state: Int) {
    if (controller.cameraInfo?.hasFlashUnit() == true) {
        controller.enableTorch(state == 1)
    }
}