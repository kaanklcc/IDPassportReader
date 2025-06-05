package com.example.idpassportreader.view.CameraPreviewScreen

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CameraPreviewArea(controller: LifecycleCameraController) {
    CameraPreview(
        controller = controller,
        modifier = Modifier.fillMaxSize()
    )
}
