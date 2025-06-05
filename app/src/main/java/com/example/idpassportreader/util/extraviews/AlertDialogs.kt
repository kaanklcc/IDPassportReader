package com.example.idpassportreader.util.extraviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.idpassportreader.R
import com.example.idpassportreader.ui.theme.anaRenkMavi

@Composable
fun SimpleAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.errorview),
                        contentDescription = "error",
                        modifier = Modifier.size(120.dp)
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hata", textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge,)
                    Spacer(Modifier.height(10.dp))
                    Text("Kimlik Doğrulama Başarısız. Tekrar Deneyin.", Modifier.padding(horizontal = 4.dp),textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,)
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Tekrar Deneyiniz", color = anaRenkMavi, style =MaterialTheme.typography.titleMedium,
                         modifier = Modifier.padding(end = 6.dp, bottom = 6.dp))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("İPTAL",color = Color.Red, style =MaterialTheme.typography.titleMedium,
                         modifier = Modifier.padding(end = 6.dp, bottom = 6.dp))


                }
            }
        )
    }
}

@Composable
fun SimpleAlertDialog3(
    showDialog: Boolean,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.errorview),
                        contentDescription = "error",
                        modifier = Modifier.size(120.dp)
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text ="HATA", textAlign = TextAlign.Center,style =MaterialTheme.typography.titleLarge,)
                    Spacer(Modifier.height(10.dp))
                    Text(text = "Maksimum Deneme Sayısına Ulaştınız.", Modifier.padding(horizontal = 4.dp),textAlign = TextAlign.Center,
                        style =MaterialTheme.typography.bodyLarge,)
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("İPTAL", fontWeight = FontWeight.Bold, color = Color.Red, textAlign = TextAlign.Center,
                        style =MaterialTheme.typography.titleMedium, modifier = Modifier.padding(end = 6.dp, bottom = 6.dp))
                }
            },

            )
    }
}

@Composable
fun SimpleAlertDialog2(
    showDialog2: Boolean,
    onConfirm: () -> Unit
) {
    if (showDialog2) {
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(painter = painterResource(R.drawable.confirm),
                        contentDescription = "error",
                        modifier = Modifier
                            .size(120.dp))

                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "ONAY", textAlign = TextAlign.Center,style =MaterialTheme.typography.titleLarge,)
                    Spacer(Modifier.height(5.dp))
                    Text(text = "Kimlik Doğrulama Başarıyla Onaylandı", Modifier.padding(horizontal = 4.dp),textAlign = TextAlign.Center,
                        style =MaterialTheme.typography.bodyLarge,)
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Tamam",style =MaterialTheme.typography.titleMedium, color = Color.Green
                    , modifier = Modifier.padding(end = 6.dp, bottom = 6.dp))
                }
            },
        )
    }
}