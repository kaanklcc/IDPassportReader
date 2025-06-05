package com.example.idpassportreader.view.CameraScreenViews

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CameraSwitchButton(controller: LifecycleCameraController) {
    IconButton(
        onClick = { //icon varsayılan olarak arka kamera başlar basınca ön kameraya geçer.
            controller.cameraSelector =
                if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
        },
        modifier = Modifier
            .offset(16.dp, 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Switch Camera",
        )

    }

}
