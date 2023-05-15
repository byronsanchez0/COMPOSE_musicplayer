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

    private val msgMutableStateFlow = MutableStateFlow<String?>(null)
    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())

    //        MutableLiveData<List<Song>>()
//    fun songs(): StateFlow<List<Song>> = songsMutableStateFlow
    val allSongs: StateFlow<List<Song>> get() = songsMutableStateFlow.asStateFlow()
    fun message(): StateFlow<String?> = msgMutableStateFlow

    fun getAllSongs() {
        val songs = songRepository.getAllSongs()
        viewModelScope.launch {
            songsMutableStateFlow.emit(songs)
        }
    }

    fun addSongToList(song: Song) {
        val success = songRepository.addNewSong(song)
//        viewModelScope.launch {
//            if (success) {
//                msgMutableStateFlow.emit("This song has been added")
//            } else {
//                msgMutableStateFlow.emit("This song is already been added")
//            }
//        }

    }
}