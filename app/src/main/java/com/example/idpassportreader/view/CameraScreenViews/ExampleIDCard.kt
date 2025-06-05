package com.example.idpassportreader.view.CameraScreenViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.idpassportreader.R

@Composable
fun ExampleIDCard() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.idphoto),
            contentDescription = "idcard",
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .height(400.dp),// %90 genişlikte olacak, ihtiyaca göre değiştirebilirsin.
            contentScale = ContentScale.Fit
        )
    }
}
