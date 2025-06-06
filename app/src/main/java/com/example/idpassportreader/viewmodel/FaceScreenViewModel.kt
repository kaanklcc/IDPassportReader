package com.example.idpassportreader.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.repository.IDPassportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaceScreenViewModel@Inject constructor (val repository: IDPassportRepository):ViewModel() {

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