package com.example.idpassportreader.retrofit

class ApiUtils {
    companion object{
        val BASE_URL="http://4.236.145.174:8081/"


        fun getNFZDao():NFCDao{
            return RetrofitClient.getClient(BASE_URL).create(NFCDao::class.java)
        }
    }
}
