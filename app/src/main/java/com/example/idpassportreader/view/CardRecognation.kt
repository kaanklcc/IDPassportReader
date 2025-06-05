@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.idpassportreader.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.idpassportreader.R
import com.example.idpassportreader.ui.theme.anaRenkMavi
import com.example.idpassportreader.ui.theme.defaultGradientBackground


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRecognation(
    navController: NavController
) {
    val gradientBackground= defaultGradientBackground()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar( //ana başlık yapılandırması
                title = {
                    Text(

                        text = "Kimlik Kartı Doğrulama",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.background(Color.Transparent),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = anaRenkMavi, // Arka plan rengi
                    titleContentColor = Color.White   // Başlık rengi
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(gradientBackground)
    ){paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.tckimlik),"tc kimlik örnek",
                Modifier.size(270.dp)
            )

            Text(

                "Lütfen Kimliğinizin arka yüzünü hazırlayınız ve kamerayı aç butonuna tıklayınız. Kamera kimliğinizi " +
                    " okuyana kadar sabit tutunuz.",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp,)
                    .padding(bottom = 50.dp) )

            Button(
                onClick ={
                    navController.navigate("cameraPreview")
                }

            ) {
                Text("Kamerayı aç",style = MaterialTheme.typography.bodyLarge,)
            }
        }

    }

}
