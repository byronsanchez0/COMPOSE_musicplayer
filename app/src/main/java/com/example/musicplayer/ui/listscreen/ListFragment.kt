package com.example.musicplayer.ui.listscreen

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.composeView
import com.example.musicplayer.databinding.FragmentSongListBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import com.example.myapplicationaaaaaaaa.ui.theme.MyApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(R.layout.fragment_song_list) {

    private val listViewModel: ListViewModel by viewModel()
    private var currentSongIndex: Int = 0

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeView {

        listViewModel.getSongs()
        val songs by listViewModel.songItems.collectAsState()
        Player.currentSongs = songs
        MyApplicationTheme() {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    bottomButtons(
                        playPlaylistClick = { playPlaylist() },
                        playRandomClick = { toggleRandomStart() },
                        goToSettingsClick = { navigateToSettingsFragment() }
                    )
                }
            ) { pad ->
                songList(
                    paddingValues = pad,
                    songs = songs,
                    deleteSong = listViewModel::deleteSongs,
                    onRefresh = { listViewModel.refreshSongs() },
                ) { song -> onSongClick(song) }

            }
        }

    }

    private fun onSongClick(song: Song) {
        val position = listViewModel.onSongClick(song)
        playSelectedSong(position)
        navigateToPlayerFragment()
    }

    private fun navigateToPlayerFragment() {
        findNavController().navigate(R.id.action_listFragment_to_musicPlayerFragment)
    }

    private fun navigateToSettingsFragment() {
        findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
    }

    private fun playSelectedSong(position: Int) {
        Player.mediaPlayer?.release()
        currentSongIndex = position
        Player.mediaPlayer = MediaPlayer.create(context, listViewModel.songItems.value[position].id)
        Player.mediaPlayer?.start()
        Player.setCurrentSong(listViewModel.songItems.value[position])
    }

    private fun playPlaylist() {
        val songs = listViewModel.songItems.value
        if (songs.isNotEmpty()) {
            currentSongIndex = 0 // Always set the index to the first song
            playSelectedSong(currentSongIndex)
            navigateToPlayerFragment()
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_songs_in_the_playlist),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun toggleRandomStart() {
        val songs = listViewModel.songItems.value
        if (songs.isNotEmpty()) {
            currentSongIndex = (0 until songs.size).random()
            playSelectedSong(currentSongIndex)
            navigateToPlayerFragment()
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_songs_in_the_playlist),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
