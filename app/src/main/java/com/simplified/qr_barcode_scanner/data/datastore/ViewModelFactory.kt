package com.simplified.qr_barcode_scanner.data.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplified.qr_barcode_scanner.data.repository.QrRepository

class PublicViewModelFactory(private val repository: QrRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PublicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PublicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
