package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.repository.SongsRepo

class MainActivity : AppCompatActivity() {
    val repo = SongsRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Player.currentSongs = repo.allSongs
//        binding.recycler.adapter = SongsAdapter(repo.allSongs)
    }



}