package com.example.musicplayer.repository

import com.example.musicplayer.R
import com.example.musicplayer.models.Song

class SongsRepo {
    val allSongs = arrayListOf(
        Song(track1, R.raw.rhapsodydawnoffire, R.drawable.rhapsody),
        Song(track2, R.raw.rufusdesolinnerbloom, R.drawable.inerbloom),
        Song(track3, R.raw.unatroca, R.drawable.exterminador)

    )


    companion object {
        const val track1: String = "Dawn of fire - Rhapsody"
        const val track2: String = "Innerbloom - RufusDeSol"
        const val track3: String = "Las monjitas - Grupo exterminador"
    }
}