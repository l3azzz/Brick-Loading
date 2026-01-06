package com.simplified.qr_barcode_scanner.data.repository

import com.simplified.qr_barcode_scanner.data.db.dao.QrScanDataDao
import com.simplified.qr_barcode_scanner.data.db.entity.QrScanData

class QrRepository(private val dao: QrScanDataDao) {

    // -------------------------------
    // INSERT
    // -------------------------------
    suspend fun insert(scanData: QrScanData) {
        dao.insert(scanData)
    }

    // -------------------------------
    // FETCH
    // -------------------------------
    suspend fun getAllScans(): List<QrScanData> {
        return dao.getAllScans()
    }

    suspend fun getActiveScans(): List<QrScanData> {
        return dao.getActiveScans()
    }

    suspend fun getFavorites(): List<QrScanData> {
        return dao.getFavoriteScans()
    }

    suspend fun getById(id: Int): QrScanData? {
        return dao.getScanById(id)
    }

    // -------------------------------
    // FAVORITE
    // -------------------------------
    suspend fun markFavorite(id: Int) {
        dao.markFavorite(id)
    }

    suspend fun unmarkFavorite(id: Int) {
        dao.unmarkFavorite(id)
    }

    // -------------------------------
    // DELETE / RESTORE
    // -------------------------------
    suspend fun softDelete(id: Int) {
        dao.softDelete(id)
    }

    suspend fun restore(id: Int) {
        dao.restoreDeleted(id)
    }

    // -------------------------------
    // EDIT
    // -------------------------------
    suspend fun editScan(
        id: Int,
        editedDate: String,
        editedTime: String
    ) {
        dao.editScan(id, editedDate, editedTime)
    }

    suspend fun updateValue(
        id: Int,
        rawValue: String,
        parsedValue: String
    ) {
        dao.updateScanValue(id, rawValue, parsedValue)
    }
}
