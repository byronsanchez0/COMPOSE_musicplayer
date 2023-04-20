package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import com.example.musicplayer.databinding.ActivitySongListBinding

class MusicPlayer : AppCompatActivity() {

    var song: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val musicTitle = binding.songTitle
        val albumPic = binding.albumPic
        val songName = intent.getStringExtra("songName")
        val mediaSong = intent.getIntExtra("songId", 0)
        song = MediaPlayer.create(this, mediaSong)
//ACA ESTA EL LIST DE SONGS DE MI DATA CLASS inCOMPLETOOOOOOOOv YA REPRODUCE Y PAUSA
        var songs = arrayListOf(
            Song(songName, mediaSong, )
        )
        song?.apply {
            setOnPreparedListener {
                start()
                binding.playbtn.setImageResource(R.drawable.pausebtn)
                binding.playbtn.setOnClickListener {

                    if (!isPlaying) {
                        start()
                        binding.playbtn.setImageResource(R.drawable.pausebtn)
                    } else {
                        pause()
                        binding.playbtn.setImageResource(R.drawable.playbtn)
                    }
                }
            }
        }
        println(mediaSong)
    }

    override fun onStop() {
        super.onStop()

        song?.pause()
    }
}