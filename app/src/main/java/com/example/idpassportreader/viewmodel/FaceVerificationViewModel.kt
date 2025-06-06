package com.example.idpassportreader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idpassportreader.model.PersonDetails
import com.example.idpassportreader.model.User
import com.example.idpassportreader.repository.IDPassportRepository
import com.example.idpassportreader.util.MatchResultAction
import com.example.idpassportreader.util.photothings.bitmapToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaceVerificationViewModel@Inject constructor (val repository: IDPassportRepository):ViewModel() {

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    private val _denemeHakki = MutableLiveData(0)
    val denemeHakki: LiveData<Int> = _denemeHakki

    private val _resultMessage = MutableLiveData<String?>()
    val resultMessage: LiveData<String?> = _resultMessage
    private val _sonucmesaj = MutableLiveData<String?>()
    val sonucmesaj: LiveData<String?> = _sonucmesaj

    private val _uiAction = MutableStateFlow<MatchResultAction?>(null)
    val uiAction = _uiAction.asStateFlow()


    fun checkNfc(user: User) {
        viewModelScope.launch {
            try {
                Log.d("MatchViewModel", "Kullanıcı verileri gönderiliyor: $user")

                val response = repository.matchUser(user)
                if (response.isSuccessful) {
                    val resultBody = response.body()
                    Log.d("MatchViewModel", "Sunucu yanıtı: $resultBody")

                    if (resultBody != null) {
                        val result = resultBody.result
                        val liveness = resultBody.liveness_score
                        val confidence = resultBody.match_confidence

                        val baseInfo = """
                        Liveness: ${"%.2f".format(liveness)}%
                        Güven: ${"%.2f".format(confidence)}%
                    """.trimIndent()

                        _responseMessage.value = when (result) {
                            "1" -> "✅ EŞLEŞTİRME BAŞARILI\n$baseInfo"
                            "2" -> "❌ EŞLEŞTİRME BAŞARISIZ\n$baseInfo"
                            "3" -> "⚠️ MANİPÜLASYON ALGILANDI\n$baseInfo"
                            else -> "❗ Bilinmeyen sonuç: $result\n$baseInfo"
                        }
                    } else {
                        _responseMessage.value = "Yanıt verisi boş"
                    }

                    Log.d("MatchViewModel", "Yanıt mesajı: ${_responseMessage.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MatchViewModel", "Sunucu hatası: $errorBody")
                    _responseMessage.value = "Sunucu hatası: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("MatchViewModel", "API isteğinde hata oluştu: ${e.message}", e)
                _responseMessage.value = "İstisna oluştu: ${e.message}"
            }
        }
    }


    fun increaseDenemeHakki() {
        _denemeHakki.value = (_denemeHakki.value ?: 0) + 1
    }

    fun resetDenemeHakki() {
        _denemeHakki.value = 0
    }

    fun clearResponseMessage() {
        _responseMessage.value = null
    }
    fun clearSonucMessage() {
        _sonucmesaj.value = null
    }
    fun clearUiAction() {
        _uiAction.value = null
    }

    fun handlePersonDetails(details: PersonDetails, selfieBase64: String) {
        details.faceImage?.let { bitmap ->
            val base64Image = bitmapToBase64(bitmap)
            val user = User(
                name = details.name ?: "",
                surname = details.surname ?: "",
                personalId = details.personalNumber ?: "",
                date = details.birthDate ?: "",
                image = base64Image,
                photo = selfieBase64
            )
            checkNfc(user)
        }
    }



    fun handleMatchResult(message: String) {
        when {
            message.startsWith("❌ EŞLEŞTİRME BAŞARISIZ") || message.startsWith("⚠️ MANİPÜLASYON ALGILANDI") -> {
                increaseDenemeHakki()
                when (denemeHakki.value) {
                    0, 1 -> _uiAction.value = MatchResultAction.ShowDialog1
                    2 -> _uiAction.value = MatchResultAction.ShowDialog3
                    else -> _uiAction.value = MatchResultAction.ShowDialog2
                }
            }
            message.startsWith("✅ EŞLEŞTİRME BAŞARILI") -> {
                _uiAction.value = MatchResultAction.ShowDialog2
            }
        }
        clearResponseMessage()
    }



}