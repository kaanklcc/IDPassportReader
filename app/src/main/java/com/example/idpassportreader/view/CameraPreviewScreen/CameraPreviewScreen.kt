package com.example.idpassportreader.view.CameraPreviewScreen

import android.annotation.SuppressLint
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.view.CameraScreenViews.CameraSwitchButton
import com.example.idpassportreader.view.CameraScreenViews.ExampleIDCard
import com.example.idpassportreader.view.CameraScreenViews.SetupMRZAnalyzer
import com.example.idpassportreader.viewmodel.AuthenticationViewModel
import com.example.idpassportreader.viewmodel.FaceScreenViewModel

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPreviewScreen(navController: NavController,
                        nfcViewModel: NFCViewModel,
                        faceScreenViewModel: FaceScreenViewModel,
                        authenticationViewModel: AuthenticationViewModel
) {
    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val mrzResult = remember { mutableStateOf("") } // MRZ sonucu burada tutulacak
    val isMRZDetected = remember { mutableStateOf(false) } // MRZ'nin tespit edilip edilmediğini kontrol etmek için

    LaunchedEffect(Unit) {
        nfcViewModel.resetData()
        faceScreenViewModel.reset()
        authenticationViewModel.reset()
    }
    //kamera kontrolleri ve görsel yakalama analiz etme
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.IMAGE_ANALYSIS
            )

        }
    }
    SetupMRZAnalyzer(
        context = context,
        controller = controller,
        onMRZDetected = { mrzText ->
            if (!isMRZDetected.value) {
                mrzResult.value = mrzText
                isMRZDetected.value = true
            }
        },
        onBlocksDetected = {  }
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {

        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CameraPreviewArea(controller)
            if (mrzResult.value.isNotEmpty()) { //mrzResult boş değilse döndür.
                LaunchedEffect(mrzResult.value) {
                    if (mrzResult.value.isNotEmpty()) {
                        val encodedMRZ = URLEncoder.encode(mrzResult.value, StandardCharsets.UTF_8.toString())
                        navController.navigate("authentication?mrz=$encodedMRZ") {
                            popUpTo("cameraPreview") { inclusive = true }
                        }
                    }
                }
            }
            ExampleIDCard()
            CameraSwitchButton(controller)
        }
    }
}
