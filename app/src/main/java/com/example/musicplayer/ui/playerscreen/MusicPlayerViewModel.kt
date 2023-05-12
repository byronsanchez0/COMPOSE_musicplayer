package com.example.musicplayer.ui.playerscreen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.R
import com.example.musicplayer.models.ManageSong
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicPlayerViewModel(private val context: Context) : ViewModel() {
//    private val songsMutableStateFlow = MutableStateFlow(listOf<Song>())
    private val title : String = ""
    private val titleMutableStateFlow = MutableStateFlow(title)
    private val uri: Uri? = null
    private val albumMutableStateFlow = MutableStateFlow(uri)

    private val playBtnStateFlow = MutableStateFlow(0)

    fun title(): MutableStateFlow<String>  = titleMutableStateFlow
    fun album(): MutableStateFlow<Uri?> = albumMutableStateFlow
    fun playBtn(): MutableStateFlow<Int> = playBtnStateFlow

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
        titleMutableStateFlow.tryEmit(nextName)
        nextAlbum?.let { album ->
            albumMutableStateFlow.tryEmit(album)
            nextSongId?.let { id ->
                sendSongChangedBroadcast(Song(nextName, id, album))
            }
        }

    }

    private fun sendSongChangedBroadcast(currentSong: Song) {
        val intent = Intent(ACTION_SONG_CHANGED)
        intent.putExtra(SONG_TITLE, currentSong.songTitle)
        intent.putExtra(SONG_URI, currentSong.id.toString())
        intent.putExtra(ALBUM_ART_URI, currentSong.album.toString())
        LocalBroadcastManager.getInstance(context.applicationContext).sendBroadcast(intent)
    }

    fun play() {
        Player.mediaPlayer?.apply {
            if (!isPlaying) {
                start()
                playBtnStateFlow.tryEmit(R.drawable.pausebtn)
            } else {
                pause()
                playBtnStateFlow.tryEmit(R.drawable.playbtn)
            }
        }
    }

    companion object {
        const val ACTION_SONG_CHANGED = "com.example.musicplayer.ACTION_SONG_CHANGED"
        const val SONG_TITLE = "song_title"
        const val SONG_URI = "song_uri"
        const val ALBUM_ART_URI = "album_art_uri"
    }
}