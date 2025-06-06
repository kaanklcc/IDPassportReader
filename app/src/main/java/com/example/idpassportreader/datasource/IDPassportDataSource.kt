package com.example.idpassportreader.datasource

import com.example.idpassportreader.model.MatchResponse
import com.example.idpassportreader.model.PersonData
import com.example.idpassportreader.model.User
import com.example.idpassportreader.retrofit.ApiUtils
import com.example.idpassportreader.util.extractBirthDate
import com.example.idpassportreader.util.extractDocumentNumber
import com.example.idpassportreader.util.extractExpirationDate
import retrofit2.Response
import android.graphics.Bitmap
import android.util.Log
import com.example.idpassportreader.retrofit.NFCDao
import com.example.idpassportreader.util.photothings.bitmapToBase64


class IDPassportDataSource(var currentPersonData: PersonData,var nfcDao:NFCDao) {
    private var base64Image: String = ""

    suspend fun matchUserPhotos(user: User): Response<MatchResponse> {
        return nfcDao.matchPhotos(user)
    }

    fun saveUserData(data: PersonData) {
        currentPersonData = data
    }

    fun getUserData(): PersonData {
        return currentPersonData
    }

    fun resetUserData() {
        currentPersonData = PersonData()
    }

    fun saveImage(base64: String) {
        base64Image = base64
    }

    fun getImage(): String {
        return base64Image
    }

    fun resetImage() {
        base64Image = ""
    }

    fun extractMrzData(mrz: String): Triple<String, String, String> {
        val documentNumber = mrz.extractDocumentNumber()
        val birthDate = mrz.extractBirthDate()
        val expirationDate = mrz.extractExpirationDate()
        return Triple(documentNumber, birthDate, expirationDate)
    }

    fun createUserFromBitmap(bitmap: Bitmap, mrz: String, selfieBase64: String): User {
        val base64Image = bitmapToBase64(bitmap)
        Log.d("Base64Image", "Base64 Encoded Image: $base64Image")

        return User(
            name = "mrz.extractName()", // Sabit verilmiş, güncellenebilir
            surname = "mrz.extractSurname()",
            personalId = "mrz.extractDocumentNumber()",
            date = "mrz.extractBirthDate()",
            image = base64Image,
            photo = selfieBase64
        )
    }
}