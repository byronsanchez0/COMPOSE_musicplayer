package com.example.musicplayer.ui.playerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.musicplayer.R
import com.example.musicplayer.models.Song

@Composable
fun musicPlayer(
    song: Song,
    onClick: (Song) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.black))
//            .padding(8.dp)
    ) {

    }

}