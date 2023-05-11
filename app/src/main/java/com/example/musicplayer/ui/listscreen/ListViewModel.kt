package com.example.musicplayer.ui.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.models.SongRepository
import com.example.musicplayer.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val repository: SongRepository) : ViewModel() {
    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())
//        MutableLiveData<List<Song>>()
//    fun songs(): StateFlow<List<Song>> = songsMutableStateFlow
    val songItems: StateFlow<List<Song>> get() = songsMutableStateFlow.asStateFlow()

     fun getSongs(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                val sapo = repository.getDefaultSongs()
                songsMutableStateFlow.emit(sapo)
                println(sapo)
                println(songItems.value)
            }
        }
    }

//    fun getSongs() {
//        val repoSongs = repository.getDefaultSongs()
//        songsMutableStateFlow.postValue(repoSongs)
////        val songs = songs().value
//
//    }

//    fun deleteSongs(song: Song) {
//        repository.deleteSong(song)
//        val repoSongs = repository.getDefaultSongs()
//        songsMutableStateFlow.postValue(repoSongs)
//    }
}

