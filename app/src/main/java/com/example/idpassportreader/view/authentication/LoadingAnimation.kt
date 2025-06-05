package com.example.idpassportreader.view.authentication

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.idpassportreader.R

@Composable
fun NFCLoadingAnimation(isScanning: Boolean) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isScanning,
        restartOnPlay = true,
        speed = 0.18f
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(100.dp)
    )
}