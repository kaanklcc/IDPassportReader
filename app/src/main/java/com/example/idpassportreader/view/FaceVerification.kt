package com.example.idpassportreader.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import com.example.idpassportreader.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.idpassportreader.ui.theme.anaRenkMavi
import com.example.idpassportreader.ui.theme.defaultGradientBackground
import com.example.idpassportreader.util.MatchResultAction
import com.example.idpassportreader.util.extraviews.PageIndicator
import com.example.idpassportreader.util.extraviews.SimpleAlertDialog
import com.example.idpassportreader.util.extraviews.SimpleAlertDialog2
import com.example.idpassportreader.util.extraviews.SimpleAlertDialog3
import com.example.idpassportreader.viewmodel.AuthenticationViewModel
import com.example.idpassportreader.viewmodel.FaceScreenViewModel
import com.example.idpassportreader.viewmodel.FaceVerificationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaceVerification(navController: NavController,faceVerificationViewModel: FaceVerificationViewModel,authenticationViewModel: AuthenticationViewModel,
                     faceScreenViewModel: FaceScreenViewModel) {
    val checkboxDurum= remember { mutableStateOf(false) } //checkbox ilk önce false yani işaretlenmemiş olarak durur.
    val responseMessage = faceVerificationViewModel.responseMessage.observeAsState()
    val denemeHakki by faceVerificationViewModel.denemeHakki.observeAsState(0) // ViewModel'den takip et
    var showDialog by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var disableButton by remember { mutableStateOf(false) }
    val uiAction by faceVerificationViewModel.uiAction.collectAsState()
    val currentDialog by faceVerificationViewModel.uiAction.collectAsState()
    val resultMessage = faceVerificationViewModel.resultMessage.observeAsState()
    val sonucmesaj = faceVerificationViewModel.sonucmesaj.observeAsState()



    LaunchedEffect(showDialog2) {
        if (showDialog2) {
            disableButton = true
        }
    }
    val gradientBackground= defaultGradientBackground()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar( //ana başlık yapılandırması
                title = {
                    Text(

                        text = "Yüz Doğrulama",
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
    ){paddingValues -> //sayfa giriş
        Box( modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            Column ( //sayfanın öğrelerinin alt alta sıralanması için column yapısı
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ){
                LaunchedEffect(responseMessage.value) {
                    responseMessage.value?.let { message ->
                        faceVerificationViewModel.handleMatchResult(message)
                    }
                }
                LaunchedEffect(uiAction) {
                    when (uiAction) {
                        MatchResultAction.ShowDialog1 -> {
                            showDialog = true

                        }
                        MatchResultAction.ShowDialog2 -> {
                            showDialog2 = true
                            faceVerificationViewModel.clearResponseMessage()
                            faceVerificationViewModel.clearSonucMessage()

                        }
                        MatchResultAction.ShowDialog3 -> {
                            showDialog3 = true
                            faceVerificationViewModel.clearResponseMessage()

                        }
                        null -> {}
                    }
                }



                when (currentDialog) {
                    MatchResultAction.ShowDialog1 -> {
                        SimpleAlertDialog(
                            showDialog = true,
                            onDismiss = { faceVerificationViewModel.clearUiAction() },
                            onConfirm = {  faceVerificationViewModel.clearUiAction()  }
                        )
                    }

                    MatchResultAction.ShowDialog2 -> {
                        SimpleAlertDialog2(
                            showDialog2 = true,
                            onConfirm = {   navController.navigate("CardRecognation") {
                                faceVerificationViewModel.resetDenemeHakki()
                                sonucmesaj.value == "" && resultMessage.value == ""
                                popUpTo("LoginSplashScreen") {
                                    inclusive = true
                                }
                                faceVerificationViewModel.clearUiAction()
                            }
                                }

                        )
                    }

                    MatchResultAction.ShowDialog3 -> {
                        SimpleAlertDialog3(
                            showDialog = true,
                            onConfirm = { authenticationViewModel.reset()
                                faceScreenViewModel.reset()
                                navController.navigate("CardRecognation") {
                                    faceVerificationViewModel.resetDenemeHakki()

                                    sonucmesaj.value == "" && resultMessage.value == ""
                                    popUpTo("LoginSplashScreen") {
                                        inclusive = true
                                    }
                                    faceVerificationViewModel.clearUiAction()
                                } }
                        )
                    }

                    null -> {}
                }
                PageIndicator(2,1, listOf("NFC",  "Yüz Tanıma")) //sayfa üstünde olan hangi sayfada ve tamamlanma durmunu gösteren fonksyionu çağırma
                Image(painter = painterResource(R.drawable.facet),
                    contentDescription = "NFC",
                    modifier = Modifier
                        .size(270.dp)
                        .padding(bottom = 24.dp))
                Text(
                    text = "Lütfen gün ışığının yoğun olmadığı bir konumda işleminizi yapınız." +
                            "Güneş gözlüğü, maske ve bere gibi aksesuar kullanıyorsanız yüz tanıma işlemine başlamadan çıkartınız.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp,) // Sağdan ve soldan 16dp boşluk
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox( //checkbox kutusu ve renk düzenlemeleri.
                        checked = checkboxDurum.value,
                        onCheckedChange = {checkboxDurum.value= it},
                        colors = CheckboxDefaults.colors(
                            checkedColor = anaRenkMavi,
                            uncheckedColor = anaRenkMavi,
                            checkmarkColor = Color.White,
                            disabledColor = anaRenkMavi,
                            disabledIndeterminateColor = anaRenkMavi
                        )
                    )
                    Text(
                       "VISIGHT TEKNOLOJİ tarafından kişisel verilerimin işlenmesini kabul ediyorum",style = MaterialTheme.typography.titleSmall,)
                }
                androidx.compose.material3.Button( //buton yapısı eğer checkbox butonuna tıklanmazsa butona tıklanmaz eğer tıklanırsa buton tıklanabilir olur
                    onClick = { navController.navigate("faceScreen") },
                    colors = ButtonDefaults.buttonColors(anaRenkMavi),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.End)
                        .padding(start = 12.dp, end = 12.dp),
                    enabled = checkboxDurum.value && denemeHakki <2 && !disableButton
                ) {
                    Text("DEVAM",style = MaterialTheme.typography.bodyLarge,)
                }
            }
        }
    }

}

