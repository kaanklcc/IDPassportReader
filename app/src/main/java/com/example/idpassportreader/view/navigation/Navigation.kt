package com.example.idpassportreader.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.idpassportreader.model.NFCViewModel
import com.example.idpassportreader.view.authentication.Authentication
import com.example.idpassportreader.view.CameraPreviewScreen.CameraPreviewScreen
import com.example.idpassportreader.view.CardRecognation
import com.example.idpassportreader.view.FaceScreen
import com.example.idpassportreader.view.FaceVerification
import com.example.idpassportreader.view.MainScreen
import com.example.idpassportreader.viewmodel.AuthenticationViewModel
import com.example.idpassportreader.viewmodel.FaceScreenViewModel
import com.example.idpassportreader.viewmodel.FaceVerificationViewModel
import com.example.idpassportreader.viewmodel.MainScreenViewModel


import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun SayfaGecisleri(
    navController: NavHostController,
    nfcViewModel: NFCViewModel,
    faceScreenViewModel: FaceScreenViewModel,
    faceVerificationViewModel: FaceVerificationViewModel,
    authenticationViewModel: AuthenticationViewModel,
    mainScreenViewModel: MainScreenViewModel



){
    NavHost(navController = navController, startDestination = "CardRecognation") {
        composable(
            route = "MainScreen?mrz={mrz}",
            arguments = listOf(navArgument("mrz") { defaultValue = "" })
        ) { backStackEntry ->
            val decodedMRZ  = URLDecoder.decode(backStackEntry.arguments?.getString("mrz") ?: "", StandardCharsets.UTF_8.toString())
            MainScreen(
                navController = navController,
                mrz = decodedMRZ,
                nfcViewModel,
                faceScreenViewModel,
                faceVerificationViewModel,
                mainScreenViewModel = mainScreenViewModel
            )
        }
        composable("cameraPreview") {
            CameraPreviewScreen(
                navController = navController,
                nfcViewModel,faceScreenViewModel,authenticationViewModel

            )
        }
        composable("authentication?mrz={mrz}",
            arguments = listOf(navArgument("mrz") { defaultValue = "" })
        ){backStackEntry ->
            val decodedMRZ  = URLDecoder.decode(backStackEntry.arguments?.getString("mrz") ?: "", StandardCharsets.UTF_8.toString())
            Authentication( navController = navController,
                mrz = decodedMRZ,
                nfcViewModel,
                faceScreenViewModel,
                faceVerificationViewModel,authenticationViewModel
            )
        }
        composable("faceVerification"){
            FaceVerification(navController,faceVerificationViewModel,authenticationViewModel,faceScreenViewModel)
        }
        composable("faceScreen"){
            FaceScreen(navController,faceScreenViewModel,
                authenticationViewModel,nfcViewModel,faceVerificationViewModel)
        }
        composable("CardRecognation"){
            CardRecognation(navController)
        }


    }

}