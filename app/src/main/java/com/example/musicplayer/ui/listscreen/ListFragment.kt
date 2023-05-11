package com.example.musicplayer.ui.listscreen

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.composeView
import com.example.musicplayer.databinding.FragmentSongListBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(R.layout.fragment_song_list) {
    private lateinit var binding: FragmentSongListBinding
    private val listViewModel: ListViewModel by viewModel()

    private var currentSongIndex: Int = 0
    private var songs: List<Song> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeView {

        listViewModel.getSongs()
        val songs by listViewModel.songItems.collectAsState()
        Scaffold (){paddingValues ->
            songList(
                modifier = Modifier.padding(paddingValues),
                songs = songs

            )
        }
        // Inflate the layout for this fragment

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        startObservables()
//        listViewModel.getSongs()
//        binding.goToSettingsbtn.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
//        }
//        binding.playInorderButton.setOnClickListener {
//            playPlaylist()
//        }
//        binding.playRandomButton.setOnClickListener {
//            toggleRandomStart()
//        }
    }

//    private fun startObservables() {
//        listViewModel.songs().observe(viewLifecycleOwner, Observer { songs ->
//            Player.currentSongs = songs
//            this.songs = songs
//            setupRecyclerView(songs)
//        })
//    }

    private fun setupRecyclerView(songs: List<Song>) {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerview.layoutManager = layoutManager

        val adapter = ListAdapter(songs, this::onSongClick, this::deleteSong)
        binding.recyclerview.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerview.context,
            layoutManager.orientation
        )
        binding.recyclerview.addItemDecoration(dividerItemDecoration)
    }

    private fun deleteSong(song: Song) {
//        listViewModel.deleteSongs(song)
    }

    private fun onSongClick(position: Int) {
        playSelectedSong(position)
        navigateToPlayerFragment()
    }

    private fun navigateToPlayerFragment() {
        findNavController().navigate(R.id.action_listFragment_to_musicPlayerFragment)
    }

    private fun playSelectedSong(position: Int) {
        Player.setCurrentSong(songs[position])
        Player.mediaPlayer?.release()
        currentSongIndex = position
        Player.mediaPlayer = MediaPlayer.create(context, songs[position].id)
        Player.mediaPlayer?.start()
    }

    private fun playPlaylist() {
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
