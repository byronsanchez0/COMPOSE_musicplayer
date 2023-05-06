package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Context
import com.example.musicplayer.models.Song

@SuppressLint("StaticFieldLeak")
object songRepository {
    private lateinit var context: Context
    var songs: List<Song> = listOf()

    fun initialize(context: Context) {
        this.context = context
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(SongsProvider.SONG_PROVIDER_URI, null, null, null, null)
        if (cursor != null) {
            songs = SongsProvider.getSongsFromCursor(cursor)
            cursor.close()
        }
    }

    fun getDefaultSongs(): List<Song> {
        return songs.take(3)
    }
}