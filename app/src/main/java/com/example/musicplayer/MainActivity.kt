package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {
    val allSongs = arrayListOf(
        Song(track1, R.raw.rhapsodydawnoffire, R.drawable.rhapsody),
        Song(track2, R.raw.rufusdesolinnerbloom, R.drawable.inerbloom),
        Song(track3, R.raw.unatroca, R.drawable.exterminador)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = SongsAdapter(allSongs)
        println(allSongs)
    }

    companion object {
        const val track1: String = "Dawn of fire - Rhapsody"
        const val track2: String = "Innerbloom - RufusDeSol"
        const val track3: String = "Las monjitas - Grupo exterminador"
    }

}