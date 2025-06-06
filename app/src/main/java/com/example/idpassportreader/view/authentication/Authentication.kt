package com.example.idpassportreader.view.authentication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.airbnb.lottie.compose.*
import com.example.idpassportreader.R
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.ui.theme.anaRenkMavi
import com.example.idpassportreader.ui.theme.defaultGradientBackground
import com.example.idpassportreader.util.extraviews.PageIndicator
import com.example.idpassportreader.util.photothings.MRZParser
import com.example.idpassportreader.util.photothings.bitmapToBase64
import com.example.idpassportreader.viewmodel.AuthenticationViewModel
import com.example.idpassportreader.viewmodel.FaceScreenViewModel
import com.example.idpassportreader.viewmodel.FaceVerificationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authentication(navController: NavController,
                   mrz: String,
                   nfcViewModel: NFCViewModel = viewModel(),
                   faceScreenViewModel: FaceScreenViewModel,
                   faceVerificationViewModel: FaceVerificationViewModel,
                   authenticationViewModel: AuthenticationViewModel
) {
    val context = LocalContext.current
    val personDetails = nfcViewModel.personDetails.observeAsState()
    var isScanning by remember { mutableStateOf(false) }
    val base64Image= faceScreenViewModel.base64Image.value
    Log.d("Base644444", "Base6444444: $base64Image")
    val isNfcReading by nfcViewModel.isNfcReading.collectAsState()
    val gradientBackground= defaultGradientBackground()

    // ModalBottomSheetState oluşturma
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) //ilk önce kapalı bir sheet daha sonrasında butona basılınca açılır
    val coroutineScope = rememberCoroutineScope()
    //modalbottomsheet kodları
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp,
            bottomEnd = 50.dp, bottomStart = 50.dp),
        sheetContent = {
            // ModalBottomSheet içeriği
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {


                Text(
                    text = "Kimliğinizi hazırlayın" ,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(Modifier.height(5.dp))
                Text(
                     text = "Kimlik kartınızı telefonun arka yüzeyinde kamera hizasında okuma başlayana " +
                            "kadar turunuz. Hata almanız durumunda,telfonunuzun kılıfı varsa " +
                            "çıkarıp tekrar deneyiniz.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
                if (!isNfcReading) {
                    Image(
                        painter = painterResource(R.drawable.read),
                        contentDescription = "kimlik okuma",
                        modifier = Modifier.size(130.dp)
                    )
                } else {
                    NFCLoadingAnimation(true)
                }
                if (mrz.isNotEmpty()) {

                    val userData = MRZParser.parse(mrz)

                    nfcViewModel.setMRZData(
                        documentNumber = userData.documentNumber,
                        birthDate = userData.birthDate,
                        expirationDate = userData.expirationDate
                    )

                    authenticationViewModel.setUserData(
                        name = userData.name,
                        surname = userData.surname,
                        birthDate = userData.birthDate,
                        personalId = userData.personalId
                    )

                    Button(
                        onClick = {
                            nfcViewModel.handleNfcIntent(context)
                            authenticationViewModel.startScanning()


                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("NFC ile Doğrula",
                            style = MaterialTheme.typography.bodyLarge,)
                    }

                }
                LaunchedEffect(personDetails.value?.faceImage) {
                    personDetails.value?.faceImage?.let { bitmap ->
                        val base64Imagenfc = bitmapToBase64(bitmap)

                        Log.d("Base64", "Base64 Image: $base64Imagenfc")
                        authenticationViewModel.setBase64Image(base64Imagenfc ?: "")
                        isScanning=false
                        navController.navigate("MainScreen")


                    }

                }
            }
        }
    ) { //ana ekran ve scaffold oluşturma
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Kimlik Kartı Okutma",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
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
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(WindowInsets.systemBars.asPaddingValues()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                PageIndicator(2,0, listOf("NFC", "Yüz Tanıma")) //pageindicatör aktif etme
                Image(
                    painter = painterResource(R.drawable.nfcsymbol),
                    contentDescription = "NFC symbol",
                    modifier = Modifier
                        .size(85.dp)
                        .offset(y = (-30.dp))
                )

                Image(
                    painter = painterResource(R.drawable.nfcrec),
                    contentDescription = "NFC",
                    modifier = Modifier
                        .size(230.dp)
                        .offset(y = (-45.dp))
                )
                Text(
                    "İşleme başlamadan önce TC Kimlik Kartınızı hazırlayınız.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .offset(y=(-45.dp))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        // ModalBottomLayoutu butona basınca ekranda gösterme
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(anaRenkMavi),
                    shape = RoundedCornerShape(8.dp), // Kenarları yuvarlat
                    modifier = Modifier
                        .fillMaxWidth() // Buton tam genişlikte
                        .height(50.dp) // Butonun yüksekliğini artır
                        .padding(horizontal = 16.dp) // Sağ ve sol boşluk

                ) {
                    Text(

                        "Kimliğimi Tara",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }

            }
        }
    }
}



