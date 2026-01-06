package com.simplified.qr_barcode_scanner.utils.mlkit

import android.graphics.ImageFormat
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


enum class QrFormatType {
    // 2D barcodes
    QR_CODE,
    AZTEC,
    DATA_MATRIX,
    PDF_417,

    // 1D barcodes
    CODE_128,
    CODE_39,
    CODE_93,
    CODABAR,
    EAN_13,
    EAN_8,
    UPC_A,
    UPC_E,
    ITF,

    // RSS / GS1
    RSS_14,
    RSS_EXPANDED,

    // Unknown / fallback
    UNKNOWN
}


private fun mapBarcodeFormat(format: Int): QrFormatType {
    return when (format) {

        // -------- 2D --------
        Barcode.FORMAT_QR_CODE -> QrFormatType.QR_CODE
        Barcode.FORMAT_AZTEC -> QrFormatType.AZTEC
        Barcode.FORMAT_DATA_MATRIX -> QrFormatType.DATA_MATRIX
        Barcode.FORMAT_PDF417 -> QrFormatType.PDF_417

        // -------- 1D --------
        Barcode.FORMAT_CODE_128 -> QrFormatType.CODE_128
        Barcode.FORMAT_CODE_39 -> QrFormatType.CODE_39
        Barcode.FORMAT_CODE_93 -> QrFormatType.CODE_93
        Barcode.FORMAT_CODABAR -> QrFormatType.CODABAR
        Barcode.FORMAT_EAN_13 -> QrFormatType.EAN_13
        Barcode.FORMAT_EAN_8 -> QrFormatType.EAN_8
        Barcode.FORMAT_UPC_A -> QrFormatType.UPC_A
        Barcode.FORMAT_UPC_E -> QrFormatType.UPC_E
        Barcode.FORMAT_ITF -> QrFormatType.ITF

        // -------- RSS / GS1 --------

        // -------- Fallback --------
        else -> QrFormatType.UNKNOWN
    }
}


class QrScanner(
    private val onQrScanned: (rawValue: String, format: QrFormatType) -> Unit,
    private val zoomCallback: (zoomRatio: Float) -> Boolean
) : ImageAnalysis.Analyzer {


    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_ALL_FORMATS
            )
            .setZoomSuggestionOptions(
                ZoomSuggestionOptions.Builder { zoomRatio ->
                    zoomCallback(zoomRatio)
                }.build()
            )
            .build()
    )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {

        val mediaImage = image.image
        if (mediaImage == null || mediaImage.format != ImageFormat.YUV_420_888) {
            image.close()
            return
        }

        val inputImage =
            InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                val barcode = barcodes.firstOrNull() ?: return@addOnSuccessListener
                val rawValue = barcode.rawValue ?: return@addOnSuccessListener

                val formatType = mapBarcodeFormat(barcode.format)

                onQrScanned(rawValue, formatType)
            }
            .addOnCompleteListener {
                image.close()
            }
    }
}
