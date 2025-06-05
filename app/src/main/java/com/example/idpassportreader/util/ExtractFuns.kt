package com.example.idpassportreader.util

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



fun AnnotatedString.Builder.appendLabeledInfo(label: String, value: String) {
    // Başlık ve değeri eklerken texti  düzenleyen fonksiyon
    this.pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp))
    this.append("$label: ")
    this.pushStyle(SpanStyle(fontWeight = FontWeight.W400))
    this.pop()
    this.pushStyle(SpanStyle(fontWeight = FontWeight.W400))
    this.append("$value\n")

}

fun String.extractMRZInfoAnnotated(): AnnotatedString {
    val lines = this.split("\n")
    if (lines.isEmpty()) return AnnotatedString("")

    val builder = AnnotatedString.Builder()

    val firstLine = lines[0]
    val secondLine = lines[1]
    val thirdLine = lines[2]

    val documentType = when {
        firstLine.startsWith("P<") -> "Passport"
        firstLine.startsWith("I<") -> "ID Card"
        else -> "Unknown"
    }

    val citizenship = if (firstLine.length > 4) firstLine.substring(2, 5) else "Unknown"
    val cleanedIDNumber = if (firstLine.length > 18) firstLine.substring(16, 30).filterNot { it == '<' || it == ' ' } else "Unknown"
    val IDNumber = if (cleanedIDNumber.isNotEmpty()) cleanedIDNumber else "Unknown"
    val dateOfBirthDay = if (secondLine.length > 5) secondLine.substring(0, 6) else "Unknown"
    val expiryDate = if (secondLine.length > 5) secondLine.substring(8, 14) else "Unknown"
    val documentNumber = if (firstLine.length > 5) firstLine.substring(5, 14).replace("O", "0") else "Unknown"

    var gender = "Unknown"
    for (char in secondLine) {
        if (char == 'M' || char == 'F') {
            gender = if (char == 'M') "Male" else "Female"
            break
        }
    }

    val Surname = thirdLine.split('<').firstOrNull() ?: "Unknown"
    val Name = thirdLine.split('<').filter { it.isNotBlank() }.getOrNull(1) ?: "Unknown"


    builder.appendLabeledInfo("Document Type", documentType)
    builder.appendLabeledInfo("Citizenship", citizenship)
    builder.appendLabeledInfo("ID Number", IDNumber)
    builder.appendLabeledInfo("Birth Day", dateOfBirthDay)
    builder.appendLabeledInfo("Gender", gender)
    builder.appendLabeledInfo("Surname", Surname)
    builder.appendLabeledInfo("Name", Name)
    builder.appendLabeledInfo("expirationDate", expiryDate)
    builder.appendLabeledInfo("documentNumber", documentNumber)

    Log.d("bilgi","Scanned Text Buffer ID Card ->>>> " +"Doc Number:"+documentNumber+" DateOfBirth:"+dateOfBirthDay + "ExpiryDate:"+expiryDate)
    //val mrzInfo: MRZInfo = buildTempMrz(documentNumber, dateOfBirthDay, expiryDate)

    Log.d("wkkk", "onNewIntent: BAC anahtar bilgileri mevcut. Okuma işlemi başlatılıyor...")
    return builder.toAnnotatedString()
}

fun String.extractDocumentNumber(): String {
    val lines = this.split("\n")
    val firstLine = lines[0]
    return if (firstLine.length > 5) firstLine.substring(5, 14).replace("O", "0") else "Unknown"
}

fun String.extractBirthDate(): String {
    val lines = this.split("\n")
    val secondLine = lines[1]
    return if (secondLine.length > 5) secondLine.substring(0, 6) else "Unknown"
}

fun String.extractExpirationDate(): String {
    val lines = this.split("\n")
    val secondLine = lines[1]
    return if (secondLine.length > 5) secondLine.substring(8, 14) else "Unknown"
}

fun String.extractDocumentNumberPassport(): String {
    val lines = this.split("\n")
    val firstLine = lines[1]
    return if (firstLine.length > 5) firstLine.substring(0, 9).replace("O", "0") else "Unknown"
}
fun String.extractBirthDatePassport(): String {
    val lines = this.split("\n")
    val secondLine = lines[1]
    return if (secondLine.length > 5) secondLine.substring(13, 19) else "Unknown"
}
fun String.extractExpirationDatePassport(): String {
    val lines = this.split("\n")
    val secondLine = lines[1]
    return if (secondLine.length > 5) secondLine.substring(21, 27) else "Unknown"
}

fun String.extractPersonaId(): String {
    val lines = this.split("\n")
    val firstLine = lines[0]
    return if (firstLine.length > 18) firstLine.substring(16, 27) else "Unknown"
}

fun String.extractName(): String {
    val lines = this.split("\n")
    val thirdLine = lines[2]
    val Name = thirdLine.split('<').filter { it.isNotBlank() }.getOrNull(1) ?: "Unknown"
    return Name
}
fun String.extractSurname(): String {
    val lines = this.split("\n")
    val thirdLine = lines[2]
    val Surname = thirdLine.split('<').firstOrNull() ?: "Unknown"
    return Surname
}


fun String.extractNamePassport(): String {
    val lines = this.split("\n")
    if (lines.size < 2) return "Unknown"

    val firstLine = lines[0]  // İlk satır (Soyadı ve Adı içerir)
    val namePart = firstLine.substring(5)  // İlk 5 karakteri at (P<GBP kısmı)

    val parts = namePart.split("<<", limit = 2) // Soyadı ve Adı ayır
    return parts.getOrNull(1)?.replace("<", " ") ?: "Unknown"
}

fun String.extractSurnamePassport(): String {
    val lines = this.split("\n")
    if (lines.size < 2) return "Unknown"

    val firstLine = lines[0]  // İlk satır (Soyadı ve Adı içerir)
    val namePart = firstLine.substring(5)  // İlk 5 karakteri at (P<GBP kısmı)

    val parts = namePart.split("<<", limit = 2) // Soyadı ve Adı ayır
    return parts.getOrNull(0)?.replace("<", "") ?: "Unknown"
}

fun String.filterMRZ(): String {
    if (this.isEmpty() || this.length < 30) return ""

    // Tüm satırları al, özel karakterleri düzelt, boşlukları kaldır, sadece geçerli karakterleri bırak
    val cleanedLines = this.split("\n")
        .map { line ->
            line
                .replace("«", "<") // özel karakter düzeltmesi
                .replace(" ", "") // boşlukları kaldır
                .map {
                    when {
                        it.isLetterOrDigit() -> it.uppercaseChar()
                        it == '<' -> it
                        else -> '<'
                    }
                }
                .joinToString("")
                .trim()
        }
        .filter { it.length in 30..44 }

    if (cleanedLines.isEmpty()) return ""

    return when {
        // Pasaport (TD3) formatı
        cleanedLines.size == 2 && cleanedLines[0].startsWith("P") -> {
            if (Regex(MRZRegex.PASSPORT_TD_3_LINE_1_REGEX).matches(cleanedLines[0]) &&
                Regex(MRZRegex.PASSPORT_TD_3_LINE_2_REGEX).matches(cleanedLines[1])) {
                cleanedLines.joinToString("\n")
            } else ""
        }

        // Kimlik kartı (TD1) formatı
        cleanedLines.size == 3 && cleanedLines[0].startsWith("I") -> {
            if (Regex(MRZRegex.ID_CARD_TD_1_LINE_1_REGEX).matches(cleanedLines[0]) &&
                Regex(MRZRegex.ID_CARD_TD_1_LINE_2_REGEX).matches(cleanedLines[1]) &&
                Regex(MRZRegex.ID_CARD_TD_1_LINE_3_REGEX).matches(cleanedLines[2])) {
                cleanedLines.joinToString("\n")
            } else ""
        }

        else -> ""
    }
}




