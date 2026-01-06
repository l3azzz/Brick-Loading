package com.simplified.qr_barcode_scanner

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.simplified.qr_barcode_scanner.navigation.SideBar.AppNavigation
import com.simplified.qr_barcode_scanner.ui.theme.QRBarcodeScannerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            QRBarcodeScannerTheme {
                AppNavigation(
                    onAppNavigationReady = {
                        lifecycleScope.launch {
                            delay(3900L)
                            if (!hasRequiredPermissions()) {
                                ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    CAMERAX_PERMISSION,
                                    0
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSION = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}
