package com.example.musicplayer.ui.listscreen

import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicplayer.models.SongRepository
import com.example.musicplayer.models.Song
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private const val MY_SONGS = "my_songs"
class ListViewModel(
    private val repository: SongRepository,
//    private val sharedPreferences: SharedPreferences,
//    private val gson: Gson,
) : ViewModel() {
    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())


    val songItems: StateFlow<List<Song>> get() = songsMutableStateFlow.asStateFlow()
    private val delBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.DeleteForever)
    val delBtn: MutableStateFlow<ImageVector> get() = delBtnStateFlow

    fun getSongs() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val songs = repository.getDefaultSongs()
                songsMutableStateFlow.emit(songs)
                println(songs)
                println(songItems.value)
            }
        }
    }

    fun refreshSongs() {
//        var newList = arrayListOf<Song>()
        viewModelScope.launch {
            val defaultSongs = repository.getDefaultSongs()
            songsMutableStateFlow.value = defaultSongs
        }
//        val mySongsId = sharedPreferences.getString(MY_SONGS, null)
//        if (mySongsId == null) {
//            sharedPreferences.edit().apply {
//                val defaultSongs = allSongs.take(3)
//                val defaultSongsId = arrayListOf<String>()
//                defaultSongs.forEach {
//                    defaultSongsId.add(it.id.toString())
//                }
//                val json = gson.toJson(defaultSongsId)
//                putString(MY_SONGS, json)
//                apply()
//                defaultSongs.forEach { newList.add(it) }
//            }


//        }
    }

    fun onSongClick(song: Song): Int {
        return songItems.value.indexOf(song)
    }


//    fun getSongs() {
//        val repoSongs = repository.getDefaultSongs()
//        songsMutableStateFlow.postValue(repoSongs)
////        val songs = songs().value
//
//    }

    fun deleteSongs(song: Song) {
        repository.deleteSong(song)
        val repoSongs = repository.getDefaultSongs()
        viewModelScope.launch {
            songsMutableStateFlow.emit(repoSongs)
        }
    }
}

