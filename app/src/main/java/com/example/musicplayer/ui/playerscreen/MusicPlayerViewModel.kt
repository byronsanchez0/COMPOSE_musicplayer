package com.example.musicplayer.ui.playerscreen

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.R
import com.example.musicplayer.models.ManageSong
import com.example.musicplayer.models.Player

class MusicPlayerViewModel(private val context: Context) : ViewModel() {
    private val titleMutableLiveData = MutableLiveData<String>()
    private val albumMutableLiveData = MutableLiveData<Uri>()
    private val playBtnLiveData = MutableLiveData<Int>()

    fun title(): LiveData<String> = titleMutableLiveData
    fun album(): LiveData<Uri> = albumMutableLiveData
    fun playBtn(): LiveData<Int> = playBtnLiveData

    fun nextSong() {
        changeSong(ManageSong.Next)
    }

    fun previousSong() {
        changeSong(ManageSong.Previous)
    }

    private fun changeSong(manageSong: ManageSong) {
        var nextSongId: Uri? = null
        var nextName = ""
        var nextAlbum: Uri? = null
        val currentSongs = Player.currentSongs
        currentSongs.forEachIndexed { i, song ->
            if (Player.currentId == song.id) {
                when (manageSong) {
                    ManageSong.Next -> {
                        var nextIndex = i + 1
                        if (nextIndex > currentSongs.size - 1) {
                            nextIndex = 0
                        }
                        nextSongId = currentSongs[nextIndex].id
                        nextName = currentSongs[nextIndex].songTitle
                        nextAlbum = currentSongs[nextIndex].album
                    }

                    ManageSong.Previous -> {
                        var previousIndex = i - 1
                        if (previousIndex < 0) {
                            previousIndex = Player.currentSongs.size - 1
                        }
                        nextSongId = currentSongs[previousIndex].id
                        nextName = currentSongs[previousIndex].songTitle
                        nextAlbum = currentSongs[previousIndex].album
                    }
                }

            }
        }
        Player.currentId = nextSongId
        Player.currentName = nextName
        Player.currentAlbum = nextAlbum
        if (Player.mediaPlayer?.isPlaying == true) {
            Player.mediaPlayer?.stop()
        }
        Player.mediaPlayer?.reset()
        Player.mediaPlayer = MediaPlayer.create(context, nextSongId)
        Player.mediaPlayer?.start()
        titleMutableLiveData.postValue(nextName)
        nextAlbum?.let {
            albumMutableLiveData.postValue(it)
        }
    }

    fun play() {
        Player.mediaPlayer?.apply {
            if (!isPlaying) {
                start()
                playBtnLiveData.postValue(R.drawable.pausebtn)
            } else {
                pause()
                playBtnLiveData.postValue(R.drawable.playbtn)
            }
        }
    }
}