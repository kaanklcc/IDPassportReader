package com.example.idpassportreader.repository

import android.graphics.Bitmap
import com.example.idpassportreader.datasource.IDPassportDataSource
import com.example.idpassportreader.model.MatchResponse
import com.example.idpassportreader.model.PersonData
import com.example.idpassportreader.model.User
import retrofit2.Response

class IDPassportRepository {
    private val IPDataSource = IDPassportDataSource()

    suspend fun matchUser(user: User): Response<MatchResponse> {
        return IPDataSource.matchUserPhotos(user)
    }

    fun saveUserData(data: PersonData){
        IPDataSource.saveUserData(data)
    }
    fun getUserData(): PersonData {
       return IPDataSource.getUserData()
    }
    fun resetUserData() {
        IPDataSource.resetUserData()
    }
    fun saveImage(base64: String) {
        IPDataSource.saveImage(base64)
    }

    fun getImage(): String {
        return IPDataSource.getImage()
    }

    fun resetImage() {
        IPDataSource.resetImage()
    }
    fun getMrzData(mrz: String): Triple<String, String, String> {
        return IPDataSource.extractMrzData(mrz)
    }

    fun buildUser(mrz: String, bitmap: Bitmap, selfieBase64: String): User {
        return IPDataSource.createUserFromBitmap(bitmap, mrz, selfieBase64)
    }
}