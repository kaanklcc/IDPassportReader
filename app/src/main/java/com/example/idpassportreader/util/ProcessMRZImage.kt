package com.example.idpassportreader.util

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer

@androidx.annotation.OptIn(ExperimentalGetImage::class)
fun processMRZImage(
    imageProxy: ImageProxy,
    textRecognizer: TextRecognizer,
    onTextDetected: (String) -> Unit,
    onBlocksDetected: (List<String>) -> Unit // yeni callback
) {
    val mediaImage = imageProxy.image // kameradan gelen görüntü erişimi sağlar.
    if (mediaImage != null) {
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        textRecognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                // Tüm metin bloklarını logla
                visionText.textBlocks.forEachIndexed { index, block ->
                    val allBlocks = visionText.textBlocks.map { it.text }
                    onBlocksDetected(allBlocks) // burada geri bildiriyoruz

                    Log.d("MRZReader", "Blok $index: ${block.text}")
                }

                // MRZ'yi filtrele ve logla
                val mrzText = visionText.textBlocks
                    .joinToString("\n") { it.text }
                    .filterMRZ() // MRZ'yi filtrele

                Log.d("MRZReader", "Filtrelenmiş MRZ Metni: $mrzText")

                if (mrzText.isNotEmpty()) {
                    onTextDetected(mrzText)
                } else {
                    Log.d("MRZReader", "MRZ tespit edilmedi.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("MRZReader", "Error reading MRZ: ${e.message}")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        Log.e("MRZReader", "ImageProxy.image is null")
        imageProxy.close()
    }
}

