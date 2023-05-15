package com.example.musicplayer.ui.playerscreen

import android.net.Uri
import com.example.musicplayer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class PlayerUiState(val songName: StateFlow<String?> = MutableStateFlow(null),
                         val image: StateFlow<Uri?> = MutableStateFlow(Uri.EMPTY),
                         val index: StateFlow<Int> = MutableStateFlow(0),
                         val playButton: StateFlow<Int> = MutableStateFlow(R.drawable.playbtn),
                         val sliderPosition: StateFlow<Float> = MutableStateFlow(0f))
