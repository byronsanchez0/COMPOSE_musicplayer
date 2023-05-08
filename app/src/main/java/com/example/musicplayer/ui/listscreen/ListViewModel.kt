package com.example.musicplayer.ui.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.SongRepository
import com.example.musicplayer.models.Song

class ListViewModel(private val repository: SongRepository) : ViewModel() {
    private val songsMutableLiveData = MutableLiveData<List<Song>>()
    fun songs(): LiveData<List<Song>> = songsMutableLiveData

    fun getSongs() {
        val repoSongs = repository.getDefaultSongs()
        songsMutableLiveData.postValue(repoSongs)
    }
}

//
//import android.content.ContentResolver
//import android.net.Uri
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.musicplayer.models.Song
//
//class ListViewModel : ViewModel() {
//    private val songsMutableLiveData = MutableLiveData<List<Song>>()
//    fun songs(): LiveData<List<Song>> = songsMutableLiveData
//
//    fun loadSongsFromProvider(contentResolver: ContentResolver) {
//        songRepository.initialize(requireContext())
//        defaultSongs = songRepository.getDefaultSongs()
//
//        val songs = mutableListOf<Song>()
//        val projection = arrayOf(
//            SongContract.Columns.ID,
//            SongContract.Columns.SONG_NAME,
//            SongContract.Columns.SONG_URI,
//            SongContract.Columns.ALBUM_ART_URI
//        )
//
//        contentResolver.query(
//            SongContract.CONTENT_URI,
//            projection,
//            null,
//            null,
//            null
//        )
//            ?.use { cursor ->
//                if (cursor.moveToFirst()) {
//                    do {
//                        val titleIndex = cursor.getColumnIndex(SongContract.Columns.SONG_NAME)
//                        val songUriIndex = cursor.getColumnIndex(SongContract.Columns.SONG_URI)
//                        val albumArtUriIndex =
//                            cursor.getColumnIndex(SongContract.Columns.ALBUM_ART_URI)
//
//                        if (titleIndex >= 0 && songUriIndex >= 0 && albumArtUriIndex >= 0) {
//                            val title = cursor.getString(titleIndex)
//                            val songUri = Uri.parse(cursor.getString(songUriIndex))
//                            val albumArtUri = Uri.parse(cursor.getString(albumArtUriIndex))
//
//                            val song = Song(title, songUri, albumArtUri)
//                            songs.add(song)
//                        }
//                    } while (cursor.moveToNext())
//                }
//            }
//
//        // Set the value of the LiveData object
//        songsMutableLiveData.postValue(songs)
//        println("songs en el viewmodel de list")
//        println(songs)
//        println("******")
//    }
//
//}