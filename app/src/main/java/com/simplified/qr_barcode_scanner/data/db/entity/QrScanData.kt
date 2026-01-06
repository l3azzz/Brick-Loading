package com.simplified.qr_barcode_scanner.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simplified.qr_barcode_scanner.utils.mlkit.QrContentType
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunction

@Entity(tableName = "scanned_qr_codes")
data class QrScanData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val scanDate: String,
    val scanTime: String,

    val scanQrFormat: String? = null,
    val scanQrRawContent: String,
    val scanQrContentType: List<QrContentType>,
    val scanQrParsedContent: String? = null,


    val isFavorite: Boolean = false,
    val isDeleted: Boolean = false,
    val isEdited: Boolean = false,

    val imageReference: String? = null,
    val lastEditedDate: String? = null,
    val lastEditedTime: String? = null,

    val scanQrFunctions: List<QrFunction>,
    val scanQrContentTypeIcon: Int,

    // 6. Optional / Future
    val scanSource: String? = null,
    val extraMetadata: String? = null


)