package com.example.musicplayer.ui.settingsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.R
import com.example.musicplayer.composeView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val settingsViewModel: SettingsViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeView {
        val snackbarHostState = remember { SnackbarHostState() }
        val composableScope = rememberCoroutineScope()
        settingsViewModel.getAllSongs()
        val allSongs by settingsViewModel.allSongs.collectAsState()
        Scaffold(
            containerColor = MaterialTheme.colorScheme.onBackground
        ) { pad ->
            Column(Modifier.padding(pad)) {
                IconButton(
                    onClick = { findNavController().navigate(R.id.action_settingsFragment_to_listFragment) }
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 3.dp, top = 7.dp),


                        tint = MaterialTheme.colorScheme.background
                    )
                }
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar {
                        Text(text = data.visuals.message, color = Color.White)
                    }
                }
                LazyColumn() {
                    items(allSongs) { song ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(
                                    border = ButtonDefaults.outlinedBorder,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = song.album),
                                contentDescription = stringResource(R.string.album_descr),
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                song.songTitle,
                                maxLines = 2,
                                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                modifier = Modifier
                                    .padding(top = 15.dp, end = 10.dp),
                            )
                            IconButton(
                                onClick = {
                                    settingsViewModel.addSongToList(song)
                                    composableScope.launch {
                                        snackbarHostState.showSnackbar("${song.songTitle} has been added")
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.AddBox,
                                    contentDescription = "add",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(end = 3.dp, top = 7.dp),


                                    tint = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}