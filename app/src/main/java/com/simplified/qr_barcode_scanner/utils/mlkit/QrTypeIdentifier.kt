package com.simplified.qr_barcode_scanner.utils.mlkit

/**
 * Represents the specific kinds of content that can be found in a QR code.
 */
enum class QrContentType {
    URL,
    GEO,
    WIFI,
    VCARD, // Contact Card
    EMAIL,
    PHONE,
    SMS,
    EVENT, // Calendar Event
    PRODUCT, // For product barcodes like EAN, UPC
    TEXT,
    UNKNOWN
}

/**
 * A stateless classifier that identifies all content types in a raw QR string.
 * Returns a prioritized list where the first item is the primary/most specific type.
 */
object QrContentClassifier {

    /**
     * Identifies all content types from a raw string and returns them as a list.
     * The first item in the list is the primary/most important type.
     * If multiple types exist, all are included in priority order.
     *
     * @param rawValue The raw string from the barcode scanner.
     * @return List of detected [QrContentType], ordered by priority (primary first).
     */
    fun classify(rawValue: String): List<QrContentType> {
        val text = rawValue.trim()
        if (text.isEmpty()) {
            return listOf(QrContentType.UNKNOWN)
        }

        val detectedTypes = mutableListOf<QrContentType>()

        // --- Detect ALL possible content types in PRIORITY ORDER ---
        // (Most specific/primary types checked first)

        // WIFI
        if (text.startsWith("WIFI:", true)) {
            detectedTypes.add(QrContentType.WIFI)
        }

        // VCARD (before email/phone since vCards contain those)
        if (text.startsWith("BEGIN:VCARD", true)) {
            detectedTypes.add(QrContentType.VCARD)
        }

        // EVENT
        if (text.startsWith("BEGIN:VEVENT", true)) {
            detectedTypes.add(QrContentType.EVENT)
        }

        // GEO
        if (text.startsWith("geo:", true) || text.contains("maps.google.com", true)) {
            detectedTypes.add(QrContentType.GEO)
        }

        // SMS
        if (text.startsWith("smsto:", true) || text.startsWith("sms:", true)) {
            detectedTypes.add(QrContentType.SMS)
        }

        // PHONE - Only strict "tel:" scheme
        if (text.startsWith("tel:", true)) {
            detectedTypes.add(QrContentType.PHONE)
        }

        // EMAIL
        if (text.startsWith("mailto:", true) ||
            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}", RegexOption.IGNORE_CASE)
                .containsMatchIn(text)
        ) {
            detectedTypes.add(QrContentType.EMAIL)
        }

        // URL
        if (Regex("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+", RegexOption.IGNORE_CASE)
                .containsMatchIn(text)
        ) {
            detectedTypes.add(QrContentType.URL)
        }

        // PRODUCT
        if (text.matches(Regex("^\\d{8}$|^\\d{12,13}$"))) {
            detectedTypes.add(QrContentType.PRODUCT)
        }

        // --- Return results ---
        return when {
            detectedTypes.isEmpty() -> listOf(QrContentType.TEXT)
            else -> detectedTypes
        }
    }

    /**
     * Gets the primary (most important) content type from a raw string.
     * This is the first item in the classify() list.
     *
     * @param rawValue The raw string from the barcode scanner.
     * @return The primary [QrContentType].
     */
    fun getPrimaryType(rawValue: String): QrContentType {
        return classify(rawValue).first()
    }
}
