package com.example.musicplayer.ui.settingsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.models.Song
import com.example.musicplayer.models.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())
    val allSongs: StateFlow<List<Song>> get() = songsMutableStateFlow.asStateFlow()

    fun getAllSongs() {
        val songs = songRepository.getAllSongs()
        viewModelScope.launch {
            songsMutableStateFlow.emit(songs)
        }
    }

    fun addSongToList(song: Song) {
        songRepository.addNewSong(song)
    }
}