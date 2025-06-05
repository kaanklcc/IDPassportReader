package com.example.idpassportreader.retrofit

import com.example.idpassportreader.model.MatchResponse
import com.example.idpassportreader.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface NFCDao {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun matchPhotos(@Body user: User): Response<MatchResponse>
}