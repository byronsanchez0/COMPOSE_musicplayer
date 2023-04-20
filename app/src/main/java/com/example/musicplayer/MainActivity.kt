package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.musicplayer.databinding.ActivityMainBinding
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    var nowPlaying = ArrayList<Pair<String, MediaPlayer>>()
    private val allSongs: Array<Field> = R.raw::class.java.fields
    private var currentSong: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        allSongs.forEach {
            val name = it.name
            val song: Int = resources.getIdentifier(name, "raw", this.packageName)
            val mediaSound = MediaPlayer.create(this, song)
            val letsPair = Pair(name, mediaSound)
            nowPlaying.add(letsPair)
            println(nowPlaying)
        }
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = SongsAdapter(nowPlaying)



//    val playButton : ImageButton = findViewById(R.id.playbtn)
//    val mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.rhapsodydawnoffire)
//    val allSongs : Array<MediaPlayer>

//    playButton.setOnClickListener{
//        if(!mediaPlayer.isPlaying){
//            mediaPlayer.start()
//            playButton.setImageResource(R.drawable.pausebtn)
//        }else{
//            mediaPlayer.pause()
//            playButton.setImageResource(R.drawable.playbtn)
//        }
//    }

    }

    fun listAllSongs(): ArrayList<String> {
        val mySongArray = arrayListOf<String>()
        for (item in allSongs) {
            mySongArray.add(item.name)
        }
        return mySongArray
    }

    //
    fun playSong(name: String) {
        currentSong = nowPlaying.find {
            it.first == name
        }?.second
        currentSong?.start()
    }

    fun pauseSong(name: String) {
        currentSong?.pause()
    }

}