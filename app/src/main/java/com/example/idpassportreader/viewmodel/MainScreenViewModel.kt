package com.example.idpassportreader.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.model.User
import com.example.idpassportreader.repository.IDPassportRepository
import com.example.idpassportreader.util.extractBirthDate
import com.example.idpassportreader.util.extractDocumentNumber
import com.example.idpassportreader.util.extractExpirationDate
import com.example.idpassportreader.util.extractName
import com.example.idpassportreader.util.extractSurname
import com.example.idpassportreader.util.photothings.bitmapToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel@Inject constructor (val repository: IDPassportRepository):ViewModel() {

    fun processMRZ(mrz: String, nfcViewModel: NFCViewModel) {
        val (documentNumber, birthDate, expirationDate) = repository.getMrzData(mrz)
        nfcViewModel.setMRZData(documentNumber, birthDate, expirationDate)
    }

    fun checkNfcBitmap(
        mrz: String,
        bitmap: Bitmap,
        faceScreenViewModel: FaceScreenViewModel,
        faceVerificationViewModel: FaceVerificationViewModel
    ) {
        val user = repository.buildUser(
            mrz = mrz,
            bitmap = bitmap,
            selfieBase64 = faceScreenViewModel.base64Image.value
        )
        faceVerificationViewModel.checkNfc(user)
    }
}