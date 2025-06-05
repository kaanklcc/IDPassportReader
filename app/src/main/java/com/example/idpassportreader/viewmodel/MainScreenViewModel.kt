package com.example.idpassportreader.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.model.User
import com.example.idpassportreader.util.extractBirthDate
import com.example.idpassportreader.util.extractDocumentNumber
import com.example.idpassportreader.util.extractExpirationDate
import com.example.idpassportreader.util.extractName
import com.example.idpassportreader.util.extractSurname
import com.example.idpassportreader.util.photothings.bitmapToBase64

class MainScreenViewModel:ViewModel() {


    fun processMRZ(mrz:String,nfcViewModel: NFCViewModel){
        val documentNumber = mrz.extractDocumentNumber()
        val birthDate = mrz.extractBirthDate()
        val expirationDate = mrz.extractExpirationDate()
        nfcViewModel.setMRZData(documentNumber, birthDate, expirationDate)
    }

    fun checkNfcBitmap(mrz:String,bitmap:Bitmap,faceScreenViewModel: FaceScreenViewModel,faceVerificationViewModel: FaceVerificationViewModel){
        val base64Imagee = bitmapToBase64(bitmap)

        // Base64 kodunu loga yazdırma
        Log.d("Base64Image", "Base64 Encoded Image: $base64Imagee")
        val user = User(
            name = "mrz.extractName()",
            surname = "mrz.extractSurname()",
            personalId = "mrz.extractDocumentNumber()",
            date = "mrz.extractBirthDate()",
            image = base64Imagee,
            photo = faceScreenViewModel.base64Image.value
        )
        faceVerificationViewModel.checkNfc(user)
    }

}