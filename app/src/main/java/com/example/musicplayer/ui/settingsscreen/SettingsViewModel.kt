package com.example.musicplayer.ui.settingsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Song
import com.example.musicplayer.models.SongRepository

class SettingsViewModel(private val songRepository: SongRepository) : ViewModel() {

    private val songsMutableLiveData = MutableLiveData<List<Song>>()
    private val messageMutableLiveData = MutableLiveData<String>()
    fun allSongs(): LiveData<List<Song>> = songsMutableLiveData
    fun message(): LiveData<String> = messageMutableLiveData

    fun getAllSongs() {
        val songs = songRepository.getAllSongs()
        songsMutableLiveData.postValue(songs)
    }

    fun addSongtoList(song: Song){
        val success = songRepository.addNewSong(song)
        if (success){
            messageMutableLiveData.postValue("This song has been added")
        }else{
            messageMutableLiveData.postValue("This song is already been added")
        }
    }
}