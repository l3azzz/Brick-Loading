package com.simplified.qr_barcode_scanner.utils.mlkit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.content.ClipboardManager
import android.content.ClipData
import android.provider.CalendarContract
import android.widget.Toast

/**
 * PURE IDENTIFIER ENUM (UI / ORDER / LABEL USE)
 */
enum class QrFunction {
    VISIT_BROWSER,
    OPEN_MAP,
    CONNECT_WIFI,
    ADD_CONTACT,
    SEND_EMAIL,
    CALL,
    SEND_SMS,
    ADD_EVENT,
    LOOKUP_PRODUCT,

    COPY,
    SHARE,
    SAVE,
    EDIT
}

/**
 * REAL EXECUTION LAYER
 * THIS is where actual shit happens
 */
object QrFunctionExecutor {

    fun execute(
        context: Context,
        function: QrFunction?,
        rawValue: String
    ) {
        try {
            when (function) {
                // --- FIXED INTENTS ---
                QrFunction.VISIT_BROWSER,
                QrFunction.OPEN_MAP,
                QrFunction.CALL,
                QrFunction.SEND_SMS,
                QrFunction.SEND_EMAIL -> {
                    // This handles all URI-based actions correctly, without adding duplicate prefixes.
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rawValue)))
                }

                // --- IMPROVED LOGIC (PLACEHOLDERS FOR NOW) ---
                QrFunction.ADD_CONTACT -> {
                    // TODO: This is a placeholder. A real implementation MUST parse the vCard.
                    // For now, it just opens the contact creation screen.
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        type = ContactsContract.Contacts.CONTENT_TYPE
                        // A real parser would extract the name, phone, email from the vCard rawValue.
                        putExtra(ContactsContract.Intents.Insert.NAME, "New Contact from QR")
                    }
                    context.startActivity(intent)
                    Toast.makeText(
                        context,
                        "Contact parsing not yet implemented.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                QrFunction.CONNECT_WIFI -> {
                    // Android 10+ requires user interaction. This is the correct way.
                    val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
                    context.startActivity(intent)
                    Toast.makeText(
                        context,
                        "Please select the Wi-Fi network from the list.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                QrFunction.ADD_EVENT -> {
                    // TODO: This is a placeholder. A real implementation MUST parse the VEVENT.
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        // A real parser would extract title, start/end dates from the rawValue.
                        putExtra(CalendarContract.Events.TITLE, "New Event from QR")
                    }
                    context.startActivity(intent)
                }

                // --- UNCHANGED & CORRECT ---
                QrFunction.LOOKUP_PRODUCT -> {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=$rawValue")
                        )
                    )
                }

                QrFunction.COPY -> {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("QR Data", rawValue))
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                }

                QrFunction.SHARE -> {
                    context.startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, rawValue)
                            },
                            "Share QR"
                        )
                    )
                }

                QrFunction.SAVE -> { /* Hook your DB here */
                }

                QrFunction.EDIT -> { /* Hook your edit screen here */
                }

                else -> {}
            }
        } catch (e: Exception) {
            // Catch exceptions for when an app can't handle the Intent (e.g., bad URI)
            Toast.makeText(context, "Could not perform action: ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
    }
}
/**
 * ANALYZER — decides ORDER only
 * First = PRIMARY
 * Last = DEFAULT
 */

//... (QrFunction enum and QrFunctionExecutor object remain the same as your provided code)
// They are already good.

/**
 * ANALYZER — a pure function that decides the ORDER and PRESENCE of actions.
 * It now correctly prioritizes the first content type.
 */
object QrFunctionAnalyzer {

    /**
     * Analyzes a prioritized list of content types and returns a final, ordered list of functions.
     *
     * @param types A List of QrContentType where the first element is considered the primary type.
     * @return An ordered List of QrFunction: [primaryAction, secondaryActions..., defaultActions...].
     */
    fun analyze(types: List<QrContentType>): List<QrFunction> {
        // If the input list is empty, just return the defaults.
        if (types.isEmpty()) {
            return listOf(QrFunction.COPY, QrFunction.SHARE, QrFunction.SAVE, QrFunction.EDIT)
        }

        // --- Step 1: Identify the single PRIMARY action ---
        // The primary action is determined ONLY by the first type in the list.
        val primaryAction = types.first().toQrFunction()

        // --- Step 2: Identify all OTHER specific actions (secondary) ---
        val secondaryActions = mutableListOf<QrFunction>()
        // Iterate over the rest of the list (if any)
        if (types.size > 1) {
            types.subList(1, types.size).forEach { contentType ->
                contentType.toQrFunction()?.let { secondaryActions.add(it) }
            }
        }

        // --- Step 3: Define the default actions ---
        val defaultActions = listOf(
            QrFunction.COPY,
            QrFunction.SHARE,
            QrFunction.SAVE,
            QrFunction.EDIT
        )

        // --- Step 4: Combine everything into the final, ordered list ---
        val finalFunctionList = mutableListOf<QrFunction>()

        // Add the primary action first, if it exists.
        primaryAction?.let { finalFunctionList.add(it) }

        // Add all unique secondary actions.
        finalFunctionList.addAll(secondaryActions)

        // Add all default actions.
        finalFunctionList.addAll(defaultActions)

        // Return the distinct list to ensure no duplicates (e.g., if a primary was also secondary).
        return finalFunctionList.distinct()
    }

    /**
     * Helper function to map a single QrContentType to its corresponding QrFunction.
     */
    private fun QrContentType.toQrFunction(): QrFunction? {
        return when (this) {
            QrContentType.URL -> QrFunction.VISIT_BROWSER
            QrContentType.GEO -> QrFunction.OPEN_MAP
            QrContentType.WIFI -> QrFunction.CONNECT_WIFI
            QrContentType.VCARD -> QrFunction.ADD_CONTACT
            QrContentType.EMAIL -> QrFunction.SEND_EMAIL
            QrContentType.PHONE -> QrFunction.CALL
            QrContentType.SMS -> QrFunction.SEND_SMS
            QrContentType.EVENT -> QrFunction.ADD_EVENT
            QrContentType.PRODUCT -> QrFunction.LOOKUP_PRODUCT
            else -> null // No specific action for TEXT, UNIVERSAL, UNKNOWN
        }
    }
}
