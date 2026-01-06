package com.simplified.qr_barcode_scanner.data.db.dao

import androidx.room.*
import com.simplified.qr_barcode_scanner.data.db.entity.QrScanData

@Dao
interface QrScanDataDao {

    // -------------------------------
    // INSERT / UPDATE / DELETE
    // -------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scanData: QrScanData)

    @Update
    suspend fun update(scanData: QrScanData)

    @Delete
    suspend fun delete(scanData: QrScanData)

    // -------------------------------
    // FETCH QUERIES
    // -------------------------------
    @Query("SELECT * FROM scanned_qr_codes ORDER BY scanDate DESC, scanTime DESC")
    suspend fun getAllScans(): List<QrScanData>

    @Query("SELECT * FROM scanned_qr_codes WHERE isFavorite = 1 ORDER BY scanDate DESC, scanTime DESC")
    suspend fun getFavoriteScans(): List<QrScanData>

    @Query("SELECT * FROM scanned_qr_codes WHERE id = :id")
    suspend fun getScanById(id: Int): QrScanData?

    @Query("SELECT * FROM scanned_qr_codes WHERE isDeleted = 0 ORDER BY scanDate DESC, scanTime DESC")
    suspend fun getActiveScans(): List<QrScanData>

    // -------------------------------
    // FAVORITE / UNFAVORITE
    // -------------------------------
    @Query("UPDATE scanned_qr_codes SET isFavorite = 1 WHERE id = :id")
    suspend fun markFavorite(id: Int)

    @Query("UPDATE scanned_qr_codes SET isFavorite = 0 WHERE id = :id")
    suspend fun unmarkFavorite(id: Int)

    // -------------------------------
    // SOFT DELETE / RESTORE
    // -------------------------------
    @Query("UPDATE scanned_qr_codes SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Int)

    @Query("UPDATE scanned_qr_codes SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreDeleted(id: Int)

    // -------------------------------
    // EDIT / UPDATE TITLE & NOTE
    // -------------------------------
    @Query("UPDATE scanned_qr_codes SET isEdited = 1, lastEditedDate = :editedDate, lastEditedTime = :editedTime WHERE id = :id")
    suspend fun editScan(
        id: Int,
        editedDate: String,
        editedTime: String
    )

    // -------------------------------
    // QUICK VALUE UPDATE (optional)
    // -------------------------------
    @Query("UPDATE scanned_qr_codes SET scanQrRawContent = :rawValue, scanQrParsedContent = :parsedValue WHERE id = :id")
    suspend fun updateScanValue(id: Int, rawValue: String, parsedValue: String)
}
