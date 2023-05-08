package com.example.musicplayer.ui.settingsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Song
import com.example.musicplayer.models.SongRepository

class SettingsViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val songsMutableLiveData = MutableLiveData<List<Song>>()
    fun allSongs(): LiveData<List<Song>> = songsMutableLiveData

    fun getAllSongs() {
        val songs = songRepository.getAllSongs()
        songsMutableLiveData.postValue(songs)
    }
}