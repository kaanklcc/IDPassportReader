package com.example.idpassportreader.model

data class UserData(
    val name: String,
    val surname: String,
    val birthDate: String,
    val personalId: String,
    val documentNumber: String,
    val expirationDate: String
)
