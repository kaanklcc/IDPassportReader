package com.example.idpassportreader.view

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.net.Uri
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.ui.theme.anaRenkMavi
import com.example.idpassportreader.util.photothings.bitmapToBase64
import com.example.idpassportreader.util.photothings.capturePhoto
import com.example.idpassportreader.util.photothings.resizeBitmap
import com.example.idpassportreader.util.photothings.uriToBitmap
import com.example.idpassportreader.view.CameraPreviewScreen.CameraPreview
import com.example.idpassportreader.viewmodel.AuthenticationViewModel
import com.example.idpassportreader.viewmodel.FaceScreenViewModel
import com.example.idpassportreader.viewmodel.FaceVerificationViewModel


@Composable
fun FaceScreen(navController: NavController, faceScreenViewModel: FaceScreenViewModel, authenticationViewModel: AuthenticationViewModel,
               nfcViewModel: NFCViewModel = viewModel(), faceVerificationViewModel: FaceVerificationViewModel) {
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    val personDetails = nfcViewModel.personDetails.observeAsState()
    val responseMessage= faceVerificationViewModel.responseMessage.observeAsState()
    var base64Image by remember { mutableStateOf<String?>(null) }
    var isProcessing by remember { mutableStateOf(false) } // Fotoğraf işleniyor animasyonu için state


    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.IMAGE_ANALYSIS
            )
        }
    }

    LaunchedEffect(responseMessage.value) {
        val message = responseMessage.value ?: return@LaunchedEffect

        when {
            message.startsWith("✅ EŞLEŞTİRME BAŞARILI") ||
                    message.startsWith("❌ EŞLEŞTİRME BAŞARISIZ") ||
                    message.startsWith("⚠️ MANİPÜLASYON ALGILANDI") -> {
                navController.navigate("faceVerification") {
                    popUpTo("faceScreen") { inclusive = true }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(controller = controller, modifier = Modifier.fillMaxSize())

        IconButton(
            onClick = { navController.navigate("faceVerification") },
            modifier = Modifier.offset(16.dp, 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Geri Dön",
            )
        }

        Box(
            modifier = Modifier
                .size(width = 230.dp, height = 310.dp)
                .align(Alignment.Center)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(100.dp))
        )

        Button(
            onClick = {
                isProcessing = true // Fotoğraf çekilince animasyonu başlat
                capturePhoto(context, controller) { uri ->
                    capturedImageUri = uri
                    capturedImageUri?.let { safeUri ->
                        val bitmap = uriToBitmap(context, safeUri)
                        val resizedBitmap = bitmap?.let { resizeBitmap(it, 300, 400) } // 300x400'e küçült
                        base64Image = resizedBitmap?.let { bitmapToBase64(it) } // Base64 çevir
                        Log.d("Base64Image", "Base64: $base64Image") // Base64'ü Logcat'e yazdır
                        Log.d("Base64Length", "Base64 Uzunluğu: ${base64Image?.length}") // Uzunluğu kontrol et
                        faceScreenViewModel.base64Image.value= base64Image?:""

                        personDetails.value?.let { details ->
                            faceVerificationViewModel.handlePersonDetails(details,base64Image!!)
                        }
                    }
                    isProcessing = false // Animasyonu bitir
                }
            },

            colors = ButtonDefaults.buttonColors(anaRenkMavi),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            Text("Fotoğraf Çek",style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,)
        }

        // Fotoğraf algılama animasyonu
        AnimatedVisibility(
            visible = isProcessing,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Arka planı koyu yapmak için
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Yüz Algılanıyor...",
                    color = Color.White,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.TopCenter) // Yazı ekranın üstünde görünsün
                        .padding(top = 50.dp)
                )
            }
        }

    }
}
