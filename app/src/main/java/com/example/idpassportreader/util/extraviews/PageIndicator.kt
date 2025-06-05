package com.example.idpassportreader.util.extraviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, labels: List<String>) { //ekranın üstünde gözzüken hangi sayfada olduğunu ve işlemin tamamlanıp tamamlanmadığını gösteren yapı
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(pageCount) { index -> //her sayfada tekrar etmesini sağlar.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = if (index == currentPage) Color.White else Color.Gray, //aynı ekrandaysa beyaz yoksa gri renk verme.
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(4.dp)) // Yuvarlak ile yazı arasında boşluk
                Text(
                    text = labels.getOrElse(index) { "" }, //yazı varsa yaz
                    style = MaterialTheme.typography.bodyLarge, // Yazı stili
                    color = Color.Black,
                    textAlign = TextAlign.Center, // Yazıyı ortala
                    modifier = Modifier.width(52.dp) // Genişliği sabitleyerek hizalamayı koru
                )
            }
        }
    }
}