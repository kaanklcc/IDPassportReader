package com.example.idpassportreader.util.photothings

import com.example.idpassportreader.model.UserData
import com.example.idpassportreader.util.extractBirthDate
import com.example.idpassportreader.util.extractBirthDatePassport
import com.example.idpassportreader.util.extractDocumentNumber
import com.example.idpassportreader.util.extractDocumentNumberPassport
import com.example.idpassportreader.util.extractExpirationDate
import com.example.idpassportreader.util.extractExpirationDatePassport
import com.example.idpassportreader.util.extractName
import com.example.idpassportreader.util.extractNamePassport
import com.example.idpassportreader.util.extractPersonaId
import com.example.idpassportreader.util.extractSurname
import com.example.idpassportreader.util.extractSurnamePassport

object MRZParser {
    fun parse(mrz: String): UserData {
        val isPassport = mrz.startsWith("P<")
        return UserData(
            name = if (isPassport) mrz.extractNamePassport() else mrz.extractName(),
            surname = if (isPassport) mrz.extractSurnamePassport() else mrz.extractSurname(),
            birthDate = if (isPassport) mrz.extractBirthDatePassport() else mrz.extractBirthDate(),
            personalId = if (isPassport) mrz.extractDocumentNumberPassport() else mrz.extractPersonaId(),
            documentNumber = if (isPassport) mrz.extractDocumentNumberPassport() else mrz.extractDocumentNumber(),
            expirationDate = if (isPassport) mrz.extractExpirationDatePassport() else mrz.extractExpirationDate()
        )
    }
}