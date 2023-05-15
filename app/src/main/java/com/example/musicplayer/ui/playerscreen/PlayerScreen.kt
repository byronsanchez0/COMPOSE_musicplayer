package com.example.musicplayer.ui.playerscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
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
            .background(color = colorResource(R.color.white))
//            .padding(8.dp)
    ) {
        IconButton(onClick = myListBtn) {
            Icon(
                Icons.Filled.PlayCircle,
                contentDescription = stringResource(R.string.start_playlist),
//                tint = MaterialTheme.

            )
        }
        Image(
            painter = rememberAsyncImagePainter(model = albumId),
            contentDescription = stringResource(R.string.album_descr),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(title)

    }


}

@Composable
fun playerButtons(
    viewModel: MusicPlayerViewModel,
    modifier: Modifier = Modifier.fillMaxWidth(),

    ) {
    val playBtn by viewModel.playBtn.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        IconButton(onClick = { viewModel.previousSong() }) {
            Icon(
                Icons.Filled.SkipPrevious,
                contentDescription = stringResource(R.string.start_playlist),

//                tint = MaterialTheme.

            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { viewModel.play() }) {
            Icon(
                playBtn,
                contentDescription = stringResource(R.string.start_playlist),
//                tint = MaterialTheme.

            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { viewModel.nextSong() }) {
            Icon(
                Icons.Filled.SkipNext,
                contentDescription = stringResource(R.string.start_playlist),
//                tint = MaterialTheme.

            )
        }


    }
}