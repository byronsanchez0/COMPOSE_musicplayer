package com.example.musicplayer.ui.listscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.R
import com.example.musicplayer.models.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun songList(
    paddingValues: PaddingValues,
    songs: List<Song>,
    onSongClick: (Song) -> Unit
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        refreshing = false
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)
    Box(
        Modifier.pullRefresh(state)
    ) {
        LazyColumn() {
            items(songs) { song ->
                itemSong(song, onSongClick)
                Divider()
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }

}

@Composable
fun itemSong(song: Song, onClick: (Song) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick(song) }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = song.album),
            contentDescription = stringResource(R.string.album_descr),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(song.songTitle)
    }

}

@Composable
fun bottomButtons(
    playPlaylistClick: () -> Unit,
    playRandomClick: () -> Unit,
    goToSettingsClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    BottomAppBar(modifier = modifier) {
        IconButton(onClick = playPlaylistClick) {
            Icon(
                Icons.Filled.PlayCircle,
                contentDescription = stringResource(R.string.start_playlist),
//                tint = MaterialTheme.

            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = playRandomClick
        ) {
            Icon(
                Icons.Filled.Shuffle,
                contentDescription = stringResource(R.string.random),
//                tint = MaterialTheme.colors.primary

            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = goToSettingsClick,
        ) {
            Icon(
                Icons.Filled.Settings,//ADD TYPE
                contentDescription = stringResource(R.string.settings),
//                tint = MaterialTheme.colors.primary
            )
        }
    }

}