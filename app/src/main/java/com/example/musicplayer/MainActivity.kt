package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    var nowPlaying = ArrayList<Pair<String, Int>>()
    private val allSongs: Array<Field> = R.raw::class.java.fields
    override fun onCreate(savedInstanceState: Bundle?) {
        allSongs.forEach {
            val name = it.name
            val song: Int = resources.getIdentifier(name, "raw", this.packageName)
            val letsPair = Pair(name, song)
            nowPlaying.add(letsPair)
            println(nowPlaying)
        }
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = SongsAdapter(nowPlaying)


        println(nowPlaying)


    }

}