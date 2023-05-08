package com.example.musicplayer.models

import android.content.Context
import android.content.SharedPreferences
import com.example.musicplayer.SongsProvider
import com.google.gson.Gson

private const val MY_SONGS = "my_songs"

class SongRepository(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    var songs: List<Song> = listOf()

    private fun getSongs() {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(SongsProvider.SONG_PROVIDER_URI, null, null, null, null)
        if (cursor != null) {
            songs = verifyMySong(SongsProvider.getSongsFromCursor(cursor))
            cursor.close()
        }
    }

    fun getAllSongs(): List<Song> {
        val contentResolver = context.contentResolver
        var allSongs = listOf<Song>()
        val cursor = contentResolver.query(SongsProvider.SONG_PROVIDER_URI, null, null, null, null)
        if (cursor != null) {
            allSongs = SongsProvider.getSongsFromCursor(cursor)
            cursor.close()
        }
        return allSongs
    }

    private fun verifyMySong(allSongs: List<Song>): List<Song> {
        var newList = arrayListOf<Song>()
        val mySongsId = sharedPreferences.getString(MY_SONGS, null)
        if (mySongsId == null) {
            sharedPreferences.edit().apply {
                val defaultSongs = allSongs.take(2)
                val defaultSongsId = arrayListOf<String>()
                defaultSongs.forEach {
                    defaultSongsId.add(it.id.toString())
                }
                val json = gson.toJson(defaultSongsId)
                putString(MY_SONGS, json)
                apply()
                defaultSongs.forEach { newList.add(it) }
            }
        } else {
            val mySongs = gson.fromJson(mySongsId, Array<String>::class.java).asList()
            mySongs.forEach { idSong ->
                allSongs.forEach { song ->
                    if (song.id.toString() == idSong) {
                        newList.add(song)
                    }
                }
            }
        }
        return newList
    }

    fun addNewSong(song: Song): Boolean {

        val mySongsId = sharedPreferences.getString(MY_SONGS, null)
        val newListSongs = arrayListOf<String>()
        if (mySongsId != null) {
            val mySongs = gson.fromJson(mySongsId, Array<String>::class.java).asList()
            mySongs.forEach { songId ->
                if (songId == song.id.toString()) return false
                newListSongs.add(songId)
            }
            newListSongs.add(song.id.toString())
            val json = gson.toJson(newListSongs)
            sharedPreferences.edit().apply {
                putString(MY_SONGS, json)
                apply()
            }
        }
        getDefaultSongs()
        return true
    }


    fun getDefaultSongs(): List<Song> {
        getSongs()
        return songs
    }

    fun deleteSong(song: Song) {
        val mySongsId = sharedPreferences.getString(MY_SONGS, null)
        val myNewList = arrayListOf<String>()
        if (mySongsId != null) {
            val mySongs = gson.fromJson(mySongsId, Array<String>::class.java).asList()
            mySongs.forEach { id ->
                if (song.id.toString() != id) {
                    myNewList.add(id)
                }
            }
            val json = gson.toJson(myNewList)
            sharedPreferences.edit().apply {
                putString(MY_SONGS, json)
                apply()
            }
        }
    }
}