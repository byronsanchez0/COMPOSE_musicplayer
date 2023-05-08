package com.example.musicplayer.models

import android.media.MediaPlayer
import android.net.Uri

object Player {
    var mediaPlayer: MediaPlayer? = null
    var currentSongs = listOf<Song>()
    var currentName: String? = null
    var currentId: Uri? = null
    var currentAlbum: Uri? = null

    fun setCurrentSong(song: Song) {
        this.currentId = song.id
        this.currentName = song.songTitle
        this.currentAlbum = song.album
    }
}