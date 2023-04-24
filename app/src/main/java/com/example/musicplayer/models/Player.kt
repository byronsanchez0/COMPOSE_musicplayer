package com.example.musicplayer.models

import android.media.MediaPlayer

object Player {
    var mediaPlayer : MediaPlayer? = null
    var currentSongs = arrayListOf<Song>()
    var currentName : String? = null
    var currentId : Int = 0
    var currentAlbum : Int = 0
}