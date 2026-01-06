package com.simplified.qr_barcode_scanner.utils.mlkit

import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object QrValueParser {

    /**
     * Parses a raw QR string into a human-readable block of text.
     *
     * @param rawValue The raw string from the scanner.
     * @param contentTypes Prioritized list of QR content types (first = primary).
     * @return Clean formatted text for display.
     */
    fun parse(rawValue: String, contentTypes: List<QrContentType>): String {
        if (contentTypes.isEmpty() || (contentTypes.size == 1 && contentTypes.first() == QrContentType.UNKNOWN)) {
            return parseText(rawValue)
        }

        if (contentTypes.size == 1) {
            return try {
                parseSingleType(rawValue, contentTypes.first())
            } catch (_: Exception) {
                parseText(rawValue)
            }
        }

        val processedSections = mutableSetOf<QrContentType>()
        return buildString {
            contentTypes.forEach { type ->
                if (type !in processedSections) {
                    val section = try {
                        parseSingleType(rawValue, type)
                    } catch (_: Exception) {
                        null
                    }
                    section?.let {
                        appendLine(it)

                        when (type) {
                            QrContentType.VCARD -> {
                                processedSections.add(QrContentType.PHONE)
                                processedSections.add(QrContentType.EMAIL)
                            }

                            QrContentType.SMS -> processedSections.add(QrContentType.PHONE)
                            else -> {}
                        }
                        processedSections.add(type)
                    }
                }
            }
        }.trim()
    }

    private fun parseSingleType(raw: String, type: QrContentType): String = when (type) {
        QrContentType.PHONE -> parsePhone(raw)
        QrContentType.SMS -> parseSms(raw)
        QrContentType.EMAIL -> parseEmail(raw)
        QrContentType.GEO -> parseGeo(raw)
        QrContentType.URL -> parseUrl(raw)
        QrContentType.WIFI -> parseWifi(raw)
        QrContentType.VCARD -> parseVCard(raw)
        QrContentType.EVENT -> parseEvent(raw)
        else -> parseText(raw)
    }

    private fun parsePhone(raw: String): String {
        val phone = raw.removePrefixIgnoreCase("tel:").trim().normalizePhoneNumber()
        return "Phone : $phone"
    }

    private fun parseSms(raw: String): String {
        val clean = when {
            raw.startsWith("smsto:", ignoreCase = true) -> raw.drop("smsto:".length)
            raw.startsWith("sms:", ignoreCase = true) -> raw.drop("sms:".length)
            else -> raw
        }
        val parts = clean.split(":", limit = 2)
        val phone = parts.getOrNull(0)?.substringBefore('?')?.trim()?.normalizePhoneNumber()
        val query = extractQueryParams(clean)
        val message =
            query["body"] ?: parts.getOrNull(1)?.let { URLDecoder.decode(it.trim(), "UTF-8") }

        return buildString {
            phone?.takeIf { it.isNotBlank() }?.let { appendLine("Phone : $it") }
            message?.takeIf { it.isNotBlank() }?.let { appendLine("Message : $it") }
        }.trim()
    }

    private fun parseEmail(raw: String): String {
        val clean = raw.removePrefixIgnoreCase("mailto:").trim()
        val params = extractQueryParams(clean)
        val email = clean.substringBefore('?').trim()
        val subject = params["subject"]
        val body = params["body"]

        return buildString {
            email.takeIf { it.isNotBlank() }?.let { appendLine("Email : $it") }
            subject?.takeIf { it.isNotBlank() }?.let { appendLine("Subject : $it") }
            body?.takeIf { it.isNotBlank() }?.let { appendLine("Body : $it") }
        }.trim()
    }

    private fun parseGeo(raw: String): String {
        val clean = raw.removePrefixIgnoreCase("geo:").trim()
        val coords = clean.substringBefore('?').split(',')
        val lat = coords.getOrNull(0)?.takeIf { it.isNotEmpty() }
        val lng = coords.getOrNull(1)?.takeIf { it.isNotEmpty() }
        val query = extractQueryParams(clean)["q"]

        return buildString {
            query?.takeIf { it.isNotBlank() }?.let { appendLine("Query : $it") }
            if (lat != null && lng != null) {
                appendLine("Latitude : $lat")
                appendLine("Longitude : $lng")
            }
        }.trim()
    }

    private fun parseUrl(raw: String): String {
        val url = if (raw.matches(
                Regex(
                    "^(https?|ftp)://.*",
                    RegexOption.IGNORE_CASE
                )
            )
        ) raw else "https://$raw"
        val main = url.substringBefore('?').substringBefore('#').trim()
        val params = extractQueryParams(url)
        val labels = mapOf(
            "utm_source" to "Source",
            "utm_campaign" to "Campaign",
            "utm_medium" to "Medium",
            "ref" to "Ref"
        )

        return buildString {
            appendLine("URL : $main")
            params.forEach { (key, value) ->
                val label = labels[key.lowercase()]
                    ?: key.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                appendLine("$label : $value")
            }
        }.trim()
    }

    private fun parseWifi(raw: String): String {
        val clean = raw.removePrefixIgnoreCase("WIFI:").trim()
        val params = extractSemicolonSeparated(clean)
        val ssid = params["S"]
        val sec = params["T"]
        val pwd = params["P"]
        val hidden = params["H"]

        return buildString {
            ssid?.takeIf { it.isNotBlank() }?.let { appendLine("SSID : $it") }
            sec?.takeIf { it.isNotBlank() }?.let { appendLine("Security : $it") }
            pwd?.takeIf { it.isNotBlank() }?.let { appendLine("Password : $it") }
            if (hidden.equals("true", ignoreCase = true)) appendLine("Hidden : Yes")
        }.trim()
    }

    private fun parseVCard(raw: String): String {
        val lines = raw.lines().map { it.trim() }.filter { it.contains(':') }
        val name = lines.findValue("FN") ?: lines.findValue("N")?.replace(';', ' ')
        val org = lines.findValue("ORG")

        return buildString {
            name?.takeIf { it.isNotBlank() }?.let { appendLine("Name : $it") }
            org?.takeIf { it.isNotBlank() }?.let { appendLine("Organization : $it") }

            lines.filter { it.startsWith("TEL", ignoreCase = true) }.forEach { line ->
                line.substringAfter(':').trim().takeIf { it.isNotBlank() }
                    ?.let { appendLine("Phone : $it") }
            }
            lines.filter { it.startsWith("EMAIL", ignoreCase = true) }.forEach { line ->
                line.substringAfter(':').trim().takeIf { it.isNotBlank() }
                    ?.let { appendLine("Email : $it") }
            }
        }.trim()
    }

    private fun parseEvent(raw: String): String {
        val lines = raw.lines().map { it.trim() }.filter { it.contains(':') }
        val summary = lines.findValue("SUMMARY")
        val location = lines.findValue("LOCATION")
        val dtStart = lines.findValue("DTSTART")?.humanizeDate()
        val dtEnd = lines.findValue("DTEND")?.humanizeDate()

        return buildString {
            summary?.takeIf { it.isNotBlank() }?.let { appendLine("Event : $it") }
            location?.takeIf { it.isNotBlank() }?.let { appendLine("Location : $it") }
            dtStart?.takeIf { it.isNotBlank() }?.let { appendLine("Starts : $it") }
            dtEnd?.takeIf { it.isNotBlank() }?.let { appendLine("Ends : $it") }
        }.trim()
    }

    private fun parseText(raw: String): String {
        val cleaned = raw.lines()
            .filterNot {
                it.startsWith("BEGIN:", ignoreCase = true) || it.startsWith(
                    "END:",
                    ignoreCase = true
                )
            }
            .joinToString("\n")
            .replace(Regex("\\s{2,}"), " ")
            .trim()
        return "Text : $cleaned"
    }

    private fun extractQueryParams(raw: String): Map<String, String> =
        raw.substringAfter('?', "")
            .split('&')
            .mapNotNull {
                it.split('=', limit = 2).takeIf { s -> s.size == 2 }?.let { (k, v) ->
                    val key = URLDecoder.decode(k, "UTF-8").trim()
                    val value = URLDecoder.decode(v, "UTF-8").trim()
                    key.takeIf { it.isNotBlank() }?.let { key to value }
                }
            }.toMap()

    private fun extractSemicolonSeparated(raw: String): Map<String, String> {
        val pattern = Regex("""(?<key>[^:;]+):(?<value>(?:\\.|[^;])*)""")
        return pattern.findAll(raw).associate {
            val key = it.groups["key"]!!.value.trim()
            val value = it.groups["value"]!!.value.replace("\\;", ";").replace("\\:", ":")
                .replace("\\\\", "\\").trim()
            key to value
        }
    }

    private fun List<String>.findValue(key: String): String? =
        this.find { it.startsWith(key, ignoreCase = true) }?.substringAfter(':')?.trim()

    private fun String.humanizeDate(): String {
        val inputFormats = listOf(
            SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            },
            SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.US),
            SimpleDateFormat("yyyyMMdd", Locale.US)
        )
        val outputFull = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US)
        val outputDateOnly = SimpleDateFormat("dd MMM yyyy", Locale.US)

        for (format in inputFormats) {
            try {
                val date = format.parse(this)
                return if (this.contains('T')) outputFull.format(date) else outputDateOnly.format(
                    date
                )
            } catch (_: Exception) {
            }
        }
        return this
    }

    private fun String.normalizePhoneNumber(): String = this.replace(Regex("[()\\-\\s]"), "")

    private fun String.removePrefixIgnoreCase(prefix: String): String =
        if (this.startsWith(prefix, ignoreCase = true)) this.drop(prefix.length) else this
}
