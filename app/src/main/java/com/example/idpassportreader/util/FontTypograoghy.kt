package com.example.idpassportreader.util

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.idpassportreader.R

val TitleFont = FontFamily(
    Font(R.font.montserrantbold)
)

val BodyFont = FontFamily(
    Font(R.font.roboto)
)
val MyTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = TitleFont,
        fontSize = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = TitleFont,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    )
)

