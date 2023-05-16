package com.example.musicplayer.ui.playerscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.R

@Composable
fun musicPlayer(
    title: String,
    albumId: Uri?,
    myListBtn: () -> Unit,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.onBackground)
            .fillMaxSize()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = myListBtn,
            modifier = Modifier
                .padding(top = 50.dp)
        ) {
            Icon(
                Icons.Filled.QueueMusic,
                modifier = Modifier
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.inversePrimary,
                contentDescription = "my list"
            )
        }
        Image(
            painter = rememberAsyncImagePainter(model = albumId),
            contentDescription = stringResource(R.string.album_descr),
            modifier = Modifier
                .size(400.dp)
                .padding(top = 70.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            maxLines = 2,
            modifier = Modifier.padding(10.dp)
        )

    }


}

@Composable
fun playerButtons(
    viewModel: MusicPlayerViewModel,

    ) {
    val playBtn by viewModel.playBtn.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        IconButton(onClick = { viewModel.previousSong() }) {
            Icon(
                Icons.Filled.SkipPrevious,
                modifier = Modifier
                    .size(50.dp),
                contentDescription = "previous",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { viewModel.play() }) {
            Icon(
                playBtn,
                contentDescription = stringResource(R.string.start_playlist),
                modifier = Modifier
                    .size(50.dp),
                tint = MaterialTheme.colorScheme.inversePrimary

            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { viewModel.nextSong() }
        ) {
            Icon(
                Icons.Filled.SkipNext,
                modifier = Modifier
                    .size(50.dp),
                contentDescription = "next",
                tint = MaterialTheme.colorScheme.inversePrimary

            )
        }


    }
}