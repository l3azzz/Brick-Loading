package com.simplified.qr_barcode_scanner.screens.create.list_data

import com.simplified.qr_barcode_scanner.R

enum class CreateItem(
    val id: String,
    val title: String,
    val icon: Int,
    val action: () -> Unit
) {
    CLIPBOARD(
        id = "clipboard",
        title = "Clipboard",
        icon = R.drawable.clipboard,
        action = {
            // TODO: Navigate to Create -> Clipboard
        }
    ),

    URL(
        id = "url",
        title = "URL",
        icon = R.drawable.url,
        action = {
            // TODO: Navigate to Create -> URL
        }
    ),

    TEXT(
        id = "text",
        title = "Text",
        icon = R.drawable.text,
        action = {
            // TODO: Navigate to Create -> Text
        }
    ),

    CONTACT(
        id = "contact",
        title = "Contact",
        icon = R.drawable.contact,
        action = {
            // TODO: Navigate to Create -> Contact
        }
    ),

    MAIL(
        id = "mail",
        title = "Mail",
        icon = R.drawable.mail,
        action = {
            // TODO: Navigate to Create -> Mail
        }
    ),

    SMS(
        id = "sms",
        title = "SMS",
        icon = R.drawable.sms,
        action = {
            // TODO: Navigate to Create -> SMS
        }
    ),

    LOCATION(
        id = "location",
        title = "Location",
        icon = R.drawable.location,
        action = {
            // TODO: Navigate to Create -> Location
        }
    ),

    CALENDAR(
        id = "calendar",
        title = "Calendar",
        icon = R.drawable.calendar,
        action = {
            // TODO: Navigate to Create -> Calendar
        }
    ),

    WIFI(
        id = "wifi",
        title = "WiFi",
        icon = R.drawable.wifi,
        action = {
            // TODO: Navigate to Create -> WiFi
        }
    ),

    MY_QR(
        id = "my_qr",
        title = "My QR",
        icon = R.drawable.myqr,
        action = {
            // TODO: Navigate to Create -> My QR
        }
    ),

    CUSTOM(
        id = "custom",
        title = "Custom",
        icon = R.drawable.custom,
        action = {
            // TODO: Navigate to Create -> Custom
        }
    ),
    EAN_8(
        id = "ean_8",
        title = "EAN_8",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> EAN_8
        }
    ),

    EAN_13(
        id = "ean_13",
        title = "EAN_13",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> EAN_13
        }
    ),

    UPC_E(
        id = "upc_e",
        title = "UPC_E",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> UPC_E
        }
    ),

    UPC_A(
        id = "upc_a",
        title = "UPC_A",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> UPC_A
        }
    ),

    CODE_39(
        id = "code_39",
        title = "CODE_39",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> CODE_39
        }
    ),

    CODE_93(
        id = "code_93",
        title = "CODE_93",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> CODE_93
        }
    ),

    CODE_128(
        id = "code_128",
        title = "CODE_128",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> CODE_128
        }
    ),

    ITF(
        id = "itf",
        title = "ITF",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> ITF
        }
    ),

    PDF_417(
        id = "pdf_417",
        title = "PDF_417",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> PDF_417
        }
    ),

    CODABAR(
        id = "codabar",
        title = "CODABAR",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> CODABAR
        }
    ),

    DATA_MATRIX(
        id = "data_matrix",
        title = "DATA_MATRIX",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> DATA_MATRIX
        }
    ),

    AZTEC(
        id = "aztec",
        title = "AZTEC",
        icon = R.drawable.barcode,
        action = {
            // TODO: Navigate to Create -> AZTEC
        }
    )


}