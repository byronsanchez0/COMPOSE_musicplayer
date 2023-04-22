package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore.Audio.Media
import android.widget.SeekBar
import com.example.musicplayer.databinding.ActivitySongListBinding

class MusicPlayer : AppCompatActivity() {

    lateinit var runnable: Runnable
    val handler = Handler()
    private var currentSong = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val musicTitle = binding.songTitle
        val albumPic = binding.albumPic
        val songBtn = binding.songListbtn
        val seekBar = binding.seekbar
        val songName = intent.getStringExtra("songName")
        val songAlbum = intent.getIntExtra("songAlbum", 0)
        var songId = intent.getIntExtra("songId", 0)
//        player = MediaPlayer.create(this, mediaSong)
        Player.mediaPlayer?.release()
        Player.mediaPlayer = MediaPlayer.create(this, songId)
        Player.mediaPlayer?.start()
        binding.playbtn.setImageResource(R.drawable.pausebtn)

        binding.playbtn.setOnClickListener {
            Player.mediaPlayer?.apply {
                if (!isPlaying) {
                    start()
                    binding.playbtn.setImageResource(R.drawable.pausebtn)
                } else {
                    pause()
                    binding.playbtn.setImageResource(R.drawable.playbtn)
                }
            }
        }
        seekBar.progress = 0
        seekBar.max = Player.mediaPlayer?.duration!!


        binding.nextbtn.setOnClickListener {
            var nextSongId = 0
            var nextName = ""
            var nextAlbum = 0
            val currentSongs = Player.currentSongs
            currentSongs.forEachIndexed { i, song ->
                if (songId == song.id) {
                    var nextIndex = i + 1
                    if (nextIndex > currentSongs.size - 1) {
                        nextIndex = 0
                    }
                    nextSongId = currentSongs[nextIndex].id
                    nextName = currentSongs[nextIndex].songTitle
                    nextAlbum = currentSongs[nextIndex].album
                }
            }
            songId = nextSongId
            if (Player.mediaPlayer?.isPlaying == true) {
                Player.mediaPlayer?.stop()
            }
            Player.mediaPlayer?.reset()
            Player.mediaPlayer = MediaPlayer.create(this, nextSongId)
            Player.mediaPlayer?.start()
            musicTitle.text = nextName
            albumPic.setImageResource(nextAlbum)
        }
        binding.previousbtn.setOnClickListener {
            var nextSongId = 0
            var nextName = ""
            var nextAlbum = 0
            val currentSongs = Player.currentSongs
            currentSongs.forEachIndexed { i, song ->
                if (songId == song.id) {
                    var nextIndex = i - 1
                    if (nextIndex < 0) {
                        nextIndex = Player.currentSongs.size - 1
                    }
                    nextSongId = currentSongs[nextIndex].id
                    nextName = currentSongs[nextIndex].songTitle
                    nextAlbum = currentSongs[nextIndex].album
                }
            }
            songId = nextSongId
            if (Player.mediaPlayer?.isPlaying == true) {
                Player.mediaPlayer?.stop()
            }
            Player.mediaPlayer?.reset()
            Player.mediaPlayer = MediaPlayer.create(this, nextSongId)
            Player.mediaPlayer?.start()
            musicTitle.text = nextName
            albumPic.setImageResource(nextAlbum)
        }

        songBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
        musicTitle.text = songName
        albumPic.setImageResource(songAlbum)

//        player?.apply {
//            setOnPreparedListener {
//                start()
//                binding.playbtn.setImageResource(R.drawable.pausebtn)
//                binding.playbtn.setOnClickListener {
//
//                    if (!isPlaying) {
//                        start()
//                        binding.playbtn.setImageResource(R.drawable.pausebtn)
//                    } else {
//                        pause()
//                        binding.playbtn.setImageResource(R.drawable.playbtn)
//                    }
//                }
//            }
//        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, change: Boolean) {
                if (change) {
                    Player.mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        runnable = Runnable {
            seekBar.progress = Player.mediaPlayer?.currentPosition ?: 0
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        Player.mediaPlayer?.setOnCompletionListener {
            binding.playbtn.setImageResource(R.drawable.pausebtn)
            seekBar.progress = 0
        }

    }// on create
//    fun previousSong(songName:){
//        if(currentSong > 0){
//            currentSong -=1
//        }else{
//            currentSong = run {
//
//            }
//        }
//    }


    override fun onPause() {
        super.onPause()

//        song?.pause()
    }
}