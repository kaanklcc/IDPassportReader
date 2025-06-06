package com.example.idpassportreader.viewmodel
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.model.PersonData
import com.example.idpassportreader.repository.IDPassportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor (val repository: IDPassportRepository): ViewModel() {
    private val _userData = MutableStateFlow(repository.getUserData())
    val userData: StateFlow<PersonData> = _userData

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    val isNfcReading = MutableStateFlow(false)

    fun startScanning() {
        _isScanning.value = true
    }

    fun stopScanning() {
        _isScanning.value = false
    }

    fun setUserData(name: String, surname: String, birthDate: String, personalId: String) {
        val updated = _userData.value.copy(
            name = name,
            surname = surname,
            birthDate = birthDate,
            personalId = personalId
        )
        repository.saveUserData(updated)
        _userData.value = updated
    }

    fun setBase64Image(image: String) {
        val updated = _userData.value.copy(base64Image = image)
        repository.saveUserData(updated)
        _userData.value = updated
    }

    fun reset() {
        repository.resetUserData()
        _userData.value = PersonData()
    }
}

