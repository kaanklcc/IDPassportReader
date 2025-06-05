package com.example.idpassportreader.viewmodel
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.datasource.IDPassportDataSource
import com.example.idpassportreader.model.PersonData
import com.example.idpassportreader.repository.IDPassportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*class AuthenticationViewModel:ViewModel() {
    private val IPrepository = IDPassportRepository()


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
}*/

// viewmodel/AuthenticationViewModel.kt
class AuthenticationViewModel: ViewModel() {
    private val repository= IDPassportRepository()

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

