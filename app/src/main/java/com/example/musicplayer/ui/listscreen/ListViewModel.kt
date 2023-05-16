package com.example.musicplayer.ui.listscreen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicplayer.SongsProvider
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
) : ViewModel() {
    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())


    val songItems: StateFlow<List<Song>> get() = songsMutableStateFlow.asStateFlow()
    private val delBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.DeleteForever)

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
        val songs = repository.refreshSongs()
        viewModelScope.launch {
            songsMutableStateFlow.emit(songs)
        }
    }

    fun onSongClick(song: Song): Int {
        return songItems.value.indexOf(song)
    }

    fun deleteSongs(song: Song) {
        repository.deleteSong(song)
        val repoSongs = repository.getDefaultSongs()
        viewModelScope.launch {
            songsMutableStateFlow.emit(repoSongs)
        }
    }
}

