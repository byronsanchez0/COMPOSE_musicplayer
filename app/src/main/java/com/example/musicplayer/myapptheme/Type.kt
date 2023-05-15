package com.example.myapplicationaaaaaaaa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R

// Set of Material typography styles to start with
private val NotoSerif = FontFamily(
    Font(R.font.notoserif_regular, FontWeight.W300),
    Font(R.font.notoserif_bold, FontWeight.W300),
    Font(R.font.notoserif_italic, FontWeight.W300)



)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NotoSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = NotoSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
/*
labelSmall = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Medium,
fontSize = 11.sp,
lineHeight = 16.sp,
letterSpacing = 0.5.sp
)
*/
)

