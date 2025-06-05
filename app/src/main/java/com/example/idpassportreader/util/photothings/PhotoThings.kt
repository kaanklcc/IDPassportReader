package com.example.idpassportreader.util.photothings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File

// Fotoğraf çekme fonksiyonu
fun capturePhoto(context: Context, controller: LifecycleCameraController, onImageCaptured: (Uri) -> Unit) {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    controller.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageCaptured(Uri.fromFile(file))
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "Fotoğraf çekme hatası: ${exception.message}", exception)
            }
        }
    )
}

// URI'yi Bitmap'e çeviren fonksiyon
fun uriToBitmap(context: Context, imageUri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Bitmap'i 300x400 piksele küçülten fonksiyon
fun resizeBitmap(bitmap: Bitmap, targetWidth: Int = 300, targetHeight: Int = 400): Bitmap {
    return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
}

// Bitmap'i Base64 formatına çeviren fonksiyon
fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream) // Kaliteyi %80 yaparak sıkıştırma uygula
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}