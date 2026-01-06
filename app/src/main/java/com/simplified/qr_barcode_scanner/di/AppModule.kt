package com.simplified.qr_barcode_scanner.di

import android.content.Context
import com.simplified.qr_barcode_scanner.data.db.AppDatabase
import com.simplified.qr_barcode_scanner.data.repository.QrRepository

interface AppModule {
    val qrRepository: QrRepository
}

class AppModuleImpl(private val appContext: Context) : AppModule {

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(appContext)
    }

    override val qrRepository: QrRepository by lazy {
        QrRepository(database.qrScanDataDao())
    }
}