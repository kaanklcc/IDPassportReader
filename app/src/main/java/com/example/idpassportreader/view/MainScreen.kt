package com.example.idpassportreader.view


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.model.User
import com.example.idpassportreader.ui.theme.anaRenkMavi
import com.example.idpassportreader.ui.theme.defaultGradientBackground
import com.example.idpassportreader.util.extractBirthDate
import com.example.idpassportreader.util.extractDocumentNumber
import com.example.idpassportreader.util.extractExpirationDate
import com.example.idpassportreader.util.extractMRZInfoAnnotated
import com.example.idpassportreader.util.photothings.bitmapToBase64
import com.example.idpassportreader.viewmodel.FaceScreenViewModel
import com.example.idpassportreader.viewmodel.FaceVerificationViewModel
import com.example.idpassportreader.viewmodel.MainScreenViewModel


import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import net.sf.scuba.data.Gender

import org.jmrtd.lds.icao.MRZInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    mrz: String,
    nfcViewModel: NFCViewModel = viewModel(),
    faceScreenViewModel: FaceScreenViewModel,
    faceVerificationviewmodel: FaceVerificationViewModel,
    mainScreenViewModel: MainScreenViewModel

) {
    val nfcData = nfcViewModel.nfcData.observeAsState("")
    val personDetails = nfcViewModel.personDetails.observeAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden) //ilk önce kapalı bir sheet daha sonrasında butona basılınca açılır
    val coroutineScope = rememberCoroutineScope()
    val base64Image= faceScreenViewModel.base64Image.value
    Log.d("Base644444", "Base6444444: $base64Image")

    val gradientBackground= defaultGradientBackground()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp,
            bottomEnd = 30.dp, bottomStart = 30.dp),
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
                    "Selfie ekranı",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                )
                Text(
                    text ="Lütfen kamerada net görülebileceğiniz iyi ışık alan bir mekana geçiş yapınız.",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( start = 8.dp, bottom = 12.dp, end = 8.dp)
                )

                Button(
                    onClick = {
                        navController.navigate("faceVerification")
                    },
                    modifier = Modifier.padding(bottom = 12.dp),colors = ButtonDefaults.buttonColors(
                        anaRenkMavi)
                ) {
                    Text("Kamera Ekranına Geç",style = MaterialTheme.typography.bodyMedium,)
                }

            }
        }
    ){
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "ID CARD/PASSPORT READER",
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
            containerColor = Color.White,
            modifier = Modifier.background(gradientBackground)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (mrz.isNotEmpty()) {
                    val extractedInfo = remember(mrz) { mrz.extractMRZInfoAnnotated() }

                    mainScreenViewModel.processMRZ(mrz,nfcViewModel)

                    if (nfcData.value.isNotEmpty()) {
                        Text(
                            text = "NFC Data: ${nfcData.value}",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp),
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }

                    Text(
                        text = extractedInfo,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(5.dp),
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 35.sp
                    )
                } else {
                    Text(text = "")
                }
                personDetails.value?.let { details ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        details.faceImage?.let { bitmap ->
                            mainScreenViewModel.checkNfcBitmap(mrz,bitmap,faceScreenViewModel,faceVerificationviewmodel)
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "ID Photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                        Text("Ad: ${details.name}", modifier = Modifier.padding(top = 6.dp), style = MaterialTheme.typography.titleMedium,)
                        Text("Soyad: ${details.surname}", modifier = Modifier.padding(top = 6.dp), style = MaterialTheme.typography.titleMedium,)
                        Text("Doğum Tarihi: ${details.birthDate}", modifier = Modifier.padding(top = 6.dp), style = MaterialTheme.typography.titleMedium,)
                        Text("Geçerlilik Tarihi: ${details.expiryDate}", modifier = Modifier.padding(top = 6.dp), style = MaterialTheme.typography.titleMedium,)
                        Text("Seri Numarası: ${details.serialNumber}", modifier = Modifier.padding(top = 6.dp), style = MaterialTheme.typography.titleMedium,)
                        Text("İhraç Makamı: ${details.nationality}", modifier = Modifier.padding(top = 6.dp),style = MaterialTheme.typography.titleMedium,)
                        Text("Kimlik Numarası: ${details.personalNumber}", modifier = Modifier.padding(top = 6.dp),style = MaterialTheme.typography.titleMedium,)
                    }
                }

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = {

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

                        "Kameraya Geç",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }

            }
        }

    }


}


