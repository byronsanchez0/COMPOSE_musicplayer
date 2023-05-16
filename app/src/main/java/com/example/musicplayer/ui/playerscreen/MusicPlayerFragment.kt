package com.example.musicplayer.ui.playerscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SliderColors
import androidx.compose.material.Snackbar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.composeView
import com.example.musicplayer.ui.playerscreen.MusicPlayerViewModel.Companion.SONG_TITLE
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicPlayerFragment : Fragment() {
    private val musicPlayerViewModel: MusicPlayerViewModel by viewModel()
    lateinit var snackbarHostState: SnackbarHostState

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeView {
        val title by musicPlayerViewModel.title.collectAsState()
        val album by musicPlayerViewModel.album.collectAsState()
        val sliderPosition by musicPlayerViewModel.sliderPosition.collectAsState()
        snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.onBackground
        ) { pad ->
            Column(
                Modifier
                    .padding(pad),
                verticalArrangement = Arrangement.Center
            ) {
                SnackbarHost(
                    hostState = snackbarHostState
                ) { data ->
                    Snackbar(
                        backgroundColor = MaterialTheme.colorScheme.onTertiary) {
                        Text(text = data.visuals.message)
                    }
                }
                musicPlayer(
                    title = title ?: "",
                    albumId = album,
                    myListBtn = { goToMyList() }
                )
                Slider(
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.onSecondary,
                        activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
                        inactiveTrackColor = MaterialTheme.colorScheme.tertiary,
                        activeTickColor = MaterialTheme.colorScheme.secondary,
                        inactiveTickColor = MaterialTheme.colorScheme.tertiary
                    ),
                    value = sliderPosition,
                    onValueChange = { position ->
                        musicPlayerViewModel.sliderPositionChanged(position)
                    },
                )
                playerButtons(
                    musicPlayerViewModel
                )

            }
        }
    }

    private val songChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && intent.action == MusicPlayerViewModel.ACTION_SONG_CHANGED) {
                val songTitle = intent.getStringExtra(SONG_TITLE) ?: ""
                lifecycleScope.launch {
                    snackbarHostState.showSnackbar(songTitle)
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(MusicPlayerViewModel.ACTION_SONG_CHANGED)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(songChangedReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(songChangedReceiver)
    }

    private fun goToMyList() {
        findNavController().navigate(R.id.action_musicPlayerFragment_to_listFragment)
    }
}