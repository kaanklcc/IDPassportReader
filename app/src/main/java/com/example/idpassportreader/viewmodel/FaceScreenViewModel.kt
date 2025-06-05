package com.example.idpassportreader.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idpassportreader.util.photothings.bitmapToBase64
import com.example.idpassportreader.util.photothings.resizeBitmap
import com.example.idpassportreader.util.photothings.uriToBitmap

class FaceScreenViewModel:ViewModel() {
    var base64Image= mutableStateOf("")

    fun reset() {
        base64Image.value = ""
    }
}