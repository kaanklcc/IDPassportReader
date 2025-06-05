package com.example.idpassportreader.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.repository.IDPassportRepository


/*class FaceScreenViewModel:ViewModel() {
    var base64Image= mutableStateOf("")

    fun reset() {
        base64Image.value = ""
    }
}*/

class FaceScreenViewModel:ViewModel() {

    private val repository= IDPassportRepository()

    var base64Image = mutableStateOf(repository.getImage())
        private set

    fun setImage(base64: String) {
        base64Image.value = base64
        repository.saveImage(base64)
    }

    fun reset() {
        repository.resetImage()
        base64Image.value = ""
    }
}