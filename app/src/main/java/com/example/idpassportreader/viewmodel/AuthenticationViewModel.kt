package com.example.idpassportreader.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.util.photothings.MRZParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationViewModel:ViewModel() {


   private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate

    private val _personalId = MutableStateFlow("")
    val personalId: StateFlow<String> = _personalId

    private val _base64nfcimage = MutableStateFlow("")
    val base64nfcimage: StateFlow<String> = _base64nfcimage
    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()
    val isNfcReading = MutableStateFlow(false)


    fun startScanning() {
        _isScanning.value = true
    }

    fun stopScanning() {
        _isScanning.value = false
    }


    fun setUserData(
        name: String,
        surname: String,
        birthDate: String,
        personalId: String
    ) {
        _name.value = name
        _surname.value = surname
        _birthDate.value = birthDate
        _personalId.value = personalId
    }
    fun setBase64Image(image: String) {
        _base64nfcimage.value = image
    }

    fun reset() {
        _name.value = ""
        _surname.value = ""
        _birthDate.value = ""
        _personalId.value = ""
        _base64nfcimage.value = ""
    }
}
