package com.example.idpassportreader.view.CameraScreenViews

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.idpassportreader.util.processMRZImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun SetupMRZAnalyzer(
    context: Context,
    controller: LifecycleCameraController,
    onMRZDetected: (String) -> Unit,
    onBlocksDetected: (List<String>) -> Unit
) {
    val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    var lastAnalyzedTimestamp by remember { mutableStateOf(0L) }
    val analysisIntervalMs = 200L

    val mrzBuffer = remember { mutableStateListOf<String>() }

    controller.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp >= analysisIntervalMs) {
            lastAnalyzedTimestamp = currentTimestamp
            processMRZImage(imageProxy, textRecognizer, { mrzText ->
                if (mrzText.isNotEmpty()) {
                    mrzBuffer.add(mrzText)
                    val requiredMatches = 1
                    if (mrzBuffer.takeLast(requiredMatches).all { it == mrzText }) {
                        onMRZDetected(mrzText)
                    }
                    while (mrzBuffer.size > 5) mrzBuffer.removeAt(0)
                }
            }, onBlocksDetected)
        } else {
            imageProxy.close()
        }
    }
}
