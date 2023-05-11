package com.example.musicplayer.ui.listscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.R
import com.example.musicplayer.models.Song

@Composable
fun songList(
    songs: List<Song>,
    modifier: Modifier
//    onSongClick: (Song) -> Unit
) {
    val itemCount by remember{ mutableStateOf(songs.size) }

//    viewModel.getSongs()
    Scaffold(
        contentColor = Color.White,
        topBar = {
            TopAppBar(backgroundColor = Color.Black) {
                Text(
                    stringResource(R.string.mylist),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(songs) {song ->
                itemSong(song)
                Divider()
            }
        }
    }


}

@Composable
fun itemSong(song: Song){
    Row(
        modifier = Modifier
//            .clickable { onClick(song) }
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