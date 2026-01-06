package com.simplified.qr_barcode_scanner.data.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.data.db.entity.QrScanData
import com.simplified.qr_barcode_scanner.data.repository.QrRepository
import com.simplified.qr_barcode_scanner.utils.mlkit.QrContentType
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PublicViewModel(private val repository: QrRepository) : ViewModel() {


    companion object {
        const val CAMERA_FRONT = 0
        const val CAMERA_BACK = 1
    }

    private val _currentCamera = MutableStateFlow(CAMERA_BACK)
    val currentCamera = _currentCamera.asStateFlow()

    fun setCurrentCamera(camera: Int) {
        _currentCamera.value = camera
    }


    private val _scanTime = MutableStateFlow("")
    val scanTime = _scanTime.asStateFlow()

    private val _scanDate = MutableStateFlow("")
    val scanDate = _scanDate.asStateFlow()

    private val _qrRawContent = MutableStateFlow("")
    val qrRawContent = _qrRawContent.asStateFlow()

    private val _qrContentTypes = MutableStateFlow<List<QrContentType>>(emptyList())
    val qrContentTypes = _qrContentTypes.asStateFlow()

    private val _parsedContent = MutableStateFlow("")
    val parsedContent = _parsedContent.asStateFlow()

    private val _qrFunctions = MutableStateFlow<List<QrFunction>>(emptyList())
    val qrFunctions = _qrFunctions.asStateFlow()

    private val _qrContentTypeIcon = MutableStateFlow(R.drawable.undefined_white)
    val qrContentTypeIcon = _qrContentTypeIcon.asStateFlow()

    private val _qrFormat = MutableStateFlow<String?>(null)
    val qrFormat = _qrFormat.asStateFlow()

    private val _imageReference = MutableStateFlow<String?>(null)
    val imageReference = _imageReference.asStateFlow()

    fun setQrData(
        time: String,
        date: String,
        qrRawContent: String,
        qrFormat: String,
        qrContentTypes: List<QrContentType>,
        parsedContent: String,
        qrFunctions: List<QrFunction>, qrContentTypeIcon: Int,
        imageReference: String? = null
    ) {
        _scanTime.value = time
        _scanDate.value = date
        _qrRawContent.value = qrRawContent
        _qrFormat.value = qrFormat
        _qrContentTypes.value = qrContentTypes
        _parsedContent.value = parsedContent
        _qrFunctions.value = qrFunctions
        _qrContentTypeIcon.value = qrContentTypeIcon
        _imageReference.value = imageReference
    }


    fun addQrData(onSaved: (QrScanData) -> Unit) {
        val time = _scanTime.value
        val date = _scanDate.value
        val qrRawContent = _qrRawContent.value
        val qrFormat = _qrFormat.value
        val qrContentTypes = _qrContentTypes.value
        val parsedContent = _parsedContent.value
        val qrFunctions = _qrFunctions.value
        val qrContentTypeIcon = _qrContentTypeIcon.value
        val imageReference = _imageReference.value


        if (qrRawContent.isEmpty()) return

        val entity = QrScanData(
            scanDate = date,
            scanTime = time,
            scanQrRawContent = qrRawContent,
            scanQrFormat = qrFormat,
            scanQrContentType = qrContentTypes,
            scanQrParsedContent = parsedContent,
            scanQrFunctions = qrFunctions,
            scanQrContentTypeIcon = qrContentTypeIcon,
            imageReference = imageReference
        )
        viewModelScope.launch {
            repository.insert(entity)
            onSaved(entity)
        }
    }

}
