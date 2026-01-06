package com.simplified.qr_barcode_scanner.utils.mlkit

import com.simplified.qr_barcode_scanner.R

/**
 * Returns the drawable resource for a list of QR content types.
 * - Single type: returns the specific drawable
 * - Multiple types: returns undefined_white
 */
fun getDrawable(types: List<QrContentType>): Int {
    return if (types.size == 1) {
        when (types.first()) {
            QrContentType.URL -> R.drawable.url
            QrContentType.GEO -> R.drawable.location
            QrContentType.WIFI -> R.drawable.wifi
            QrContentType.VCARD -> R.drawable.contact
            QrContentType.EMAIL -> R.drawable.mail
            QrContentType.PHONE -> R.drawable.phone
            QrContentType.SMS -> R.drawable.sms
            QrContentType.EVENT -> R.drawable.calendar
            QrContentType.PRODUCT -> R.drawable.product
            QrContentType.TEXT -> R.drawable.text
            QrContentType.UNKNOWN -> R.drawable.undefined_white
        }
    } else {
        // More than one type → dynamic
        R.drawable.undefined_white
    }
}