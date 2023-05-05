package com.example.musicplayer

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.musicplayer.models.Song

class songsProvider : ContentProvider() {
    private val _deletedSongs = mutableSetOf<String>()
    private val _songs = mutableListOf(
        Song(
            SONG_NAME_ONE,
            Uri.parse("${URI_PATH}${R.raw.rhapsodydawnoffire}"),
            Uri.parse("${URI_PATH}${R.drawable.rhapsody}")
        ),
        Song(
            SONG_NAME_TWO,
            Uri.parse("${URI_PATH}${R.raw.rufusdesolinnerbloom}"),
            Uri.parse("${URI_PATH}${R.drawable.inerbloom}")
        ),
        Song(
            SONG_NAME_THREE,
            Uri.parse("${URI_PATH}${R.raw.unatroca}"),
            Uri.parse("${URI_PATH}${R.drawable.exterminador}")
        )
    )

    val songs: List<Song>
        get() = _songs.toList()

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var matrixCursor = MatrixCursor(arrayOf(ID, SONG_NAME, SONG_URI, ALBUM_ART_URI, ALBUM_ART_URI))
        _songs.forEachIndexed{index, song ->
            if(!_deletedSongs.contains(song.songTitle)){
                matrixCursor.addRow(
                    arrayOf(
                        index,
                        song.songTitle,
                        song.id.toString(),
                        song.album.toString()
                    )
                )
            }
        }
        return matrixCursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null){
            throw IllegalArgumentException("ContentValues cannot be null")
        }
        val title = values.getAsString(SONG_NAME)
        val songUri = Uri.parse(values.getAsString(SONG_URI))
        val albumArtUri = Uri.parse(values.getAsString(ALBUM_ART_URI))

        val song = Song(title, songUri, albumArtUri)
        _songs.add(song)
        return ContentUris.withAppendedId(uri, (_songs.size - 1).toLong())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Not supported. Read-only provider.")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Not supported. Read-only provider.")
    }

    companion object {
        const val SONG_NAME_ONE: String = "Bar Liar"
        const val SONG_NAME_TWO: String = "Girls Like You"
        const val SONG_NAME_THREE: String = "See You Again"
        private const val AUTHORITY = "com.example.musicplayer.provider"
        const val URI_PATH = "android.resource://com.example.musicplayer/"
        const val ID = "_id"
        const val SONG_NAME = "song_name"
        const val SONG_URI = "song_uri"
        const val ALBUM_ART_URI = "album_art_uri"
        val SONG_PROVIDER_URI: Uri = Uri.parse("content://$AUTHORITY/songs")

        fun getSongsFromCursor(cursor: Cursor): List<Song> {
            val songs = mutableListOf<Song>()

            val nameColumnIndex = cursor.getColumnIndex(SONG_NAME)
            val songUriColumnIndex = cursor.getColumnIndex(SONG_URI)
            val albumArtUriColumnIndex = cursor.getColumnIndex(ALBUM_ART_URI)

            if (nameColumnIndex == -1 || songUriColumnIndex == -1 || albumArtUriColumnIndex == -1) {
                return emptyList()
            }

            while (cursor.moveToNext()) {
                val title = cursor.getString(nameColumnIndex)
                val songUri = Uri.parse(cursor.getString(songUriColumnIndex))
                val albumArtUri = Uri.parse(cursor.getString(albumArtUriColumnIndex))

                val song = Song(title, songUri, albumArtUri)
                songs.add(song)
            }
            return songs
        }
    }
}