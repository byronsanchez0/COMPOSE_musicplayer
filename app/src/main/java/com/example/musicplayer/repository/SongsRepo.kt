package com.example.musicplayer.repository

import com.example.musicplayer.R
import com.example.musicplayer.models.Song

class SongsRepo {
    val allSongs = arrayListOf(
        Song(track1, R.raw.rhapsodydawnoffire, R.drawable.rhapsody),
        Song(track2, R.raw.rufusdesolinnerbloom, R.drawable.inerbloom),
        Song(track3, R.raw.unatroca, R.drawable.exterminador),
        Song(track4, R.raw.unatroca, R.drawable.exterminador),
        Song(track5, R.raw.unatroca, R.drawable.exterminador),
        Song(track6, R.raw.unatroca, R.drawable.exterminador),
        Song(track7, R.raw.unatroca, R.drawable.exterminador),
        Song(track8, R.raw.unatroca, R.drawable.exterminador),
        Song(track9, R.raw.unatroca, R.drawable.exterminador),
        Song(track10, R.raw.unatroca, R.drawable.exterminador),




    )


    companion object {
        const val track1: String = "Dawn of fire - Rhapsody"
        const val track2: String = "Innerbloom - RufusDeSol"
        const val track3: String = "Las monjitas - Grupo exterminador"
        const val track4: String = "Dawn of fire - Rhapsody"
        const val track5: String = "Innerbloom - RufusDeSol"
        const val track6: String = "Las monjitas - Grupo exterminador"
        const val track7: String = "Dawn of fire - Rhapsody"
        const val track8: String = "Innerbloom - RufusDeSol"
        const val track9: String = "Las monjitas - Grupo exterminador"
        const val track10: String = "Las monjitas - Grupo exterminador"
    }
}