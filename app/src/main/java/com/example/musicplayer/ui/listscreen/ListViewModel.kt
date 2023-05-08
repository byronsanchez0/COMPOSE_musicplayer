package com.example.musicplayer.ui.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.SongRepository
import com.example.musicplayer.models.Song

class ListViewModel(private val repository: SongRepository) : ViewModel() {
    private val songsMutableLiveData = MutableLiveData<List<Song>>()
    fun songs(): LiveData<List<Song>> = songsMutableLiveData

    fun getSongs() {
        val repoSongs = repository.getDefaultSongs()
        songsMutableLiveData.postValue(repoSongs)
    }
    fun deleteSongs(song: Song) {
        repository.deleteSong(song)
        val repoSongs = repository.getDefaultSongs()
        songsMutableLiveData.postValue(repoSongs)
    }
}

