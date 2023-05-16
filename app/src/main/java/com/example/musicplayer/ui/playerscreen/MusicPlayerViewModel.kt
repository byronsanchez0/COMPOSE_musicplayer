package com.example.musicplayer.ui.playerscreen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.models.ManageSong
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicPlayerViewModel(private val context: Context) : ViewModel() {
    private val titleMutableStateFlow = MutableStateFlow<String?>(null)
    private val albumMutableStateFlow = MutableStateFlow<Uri?>(null)
    private val playBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.PauseCircle)
    private val sliderPositionSateFlow = MutableStateFlow<Float>(0f)
    lateinit var runnable: Runnable
    val handler = Handler()


    init {
        runnable = Runnable {
            Player.mediaPlayer?.let { mediaPlayer ->
                val currentPosition = mediaPlayer.currentPosition.toFloat()
                val duration = mediaPlayer.duration.toFloat()
                val progress = currentPosition / duration
                viewModelScope.launch {
                    sliderPositionSateFlow.emit(progress)
                }
            }

//            binding.seekbar.progress = Player.mediaPlayer?.currentPosition ?: 0
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        viewModelScope.launch {
            titleMutableStateFlow.emit(Player.currentName)
            albumMutableStateFlow.emit(Player.currentAlbum)
        }
    }

    val title: MutableStateFlow<String?> get() = titleMutableStateFlow
    val album: MutableStateFlow<Uri?> get() = albumMutableStateFlow
    val playBtn: MutableStateFlow<ImageVector> get() = playBtnStateFlow

    val sliderPosition: MutableStateFlow<Float> get() = sliderPositionSateFlow


    fun sliderPositionChanged(newSliderPosition: Float) {
        Player.mediaPlayer?.let { mediaPlayer ->
            val newPosition = (newSliderPosition * mediaPlayer.duration).toInt()
            mediaPlayer.seekTo(newPosition)
        }
    }

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
                viewModelScope.launch {
                    playBtnStateFlow.emit(Icons.Filled.PauseCircle)
                }


            } else {
                pause()
                viewModelScope.launch {
                    playBtnStateFlow.emit(Icons.Filled.PlayCircle)
                }
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