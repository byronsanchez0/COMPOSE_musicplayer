//package com.example.musicplayer
//
//import android.content.Intent
//import android.media.MediaPlayer
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Handler
//import android.widget.SeekBar
//import com.example.musicplayer.databinding.ActivitySongListBinding
//import com.example.musicplayer.models.ManageSong
//import com.example.musicplayer.models.Player
//
//class MusicPlayer : AppCompatActivity() {
//
//    lateinit var binding : ActivitySongListBinding
//    lateinit var runnable: Runnable
//    val handler = Handler()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//         binding = ActivitySongListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.playbtn.setImageResource(R.drawable.pausebtn)
//        binding.seekbar.progress = 0
//        binding.seekbar.max = Player.mediaPlayer?.duration!!
//
//        binding.playbtn.setOnClickListener {
//            play()
//        }
//
//        binding.nextbtn.setOnClickListener {
//            nextSong()
//        }
//        binding.previousbtn.setOnClickListener {
//            previousSong()
//        }
//
//        binding.songListbtn.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            this.startActivity(intent)
//        }
//        binding.songTitle.text = Player.currentName
//        binding.albumPic.setImageResource(Player.currentAlbum)
//
//        //********SeekBar Management
//        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, change: Boolean) {
//                if (change) {
//                    Player.mediaPlayer?.seekTo(progress)
//                }
//            }
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//        })
//        runnable = Runnable {
//            binding.seekbar.progress = Player.mediaPlayer?.currentPosition ?: 0
//            handler.postDelayed(runnable, 1000)
//        }
//        handler.postDelayed(runnable, 1000)
//
//        Player.mediaPlayer?.setOnCompletionListener {
//            binding.playbtn.setImageResource(R.drawable.pausebtn)
//            binding.seekbar.progress = 0
//        }
//        //******SeekBar Management
//
//    }
//
//    private fun nextSong() {
//        changeSong(ManageSong.Next)
//    }
//
//    private fun previousSong() {
//        changeSong(ManageSong.Previous)
//    }
//
//    private fun changeSong(manageSong: ManageSong) {
//        var nextSongId = 0
//        var nextName = ""
//        var nextAlbum = 0
//        val currentSongs = Player.currentSongs
//        currentSongs.forEachIndexed { i, song ->
//            if (Player.currentId == song.id) {
//                when (manageSong) {
//                    ManageSong.Next -> {
//                        var nextIndex = i + 1
//                        if (nextIndex > currentSongs.size - 1) {
//                            nextIndex = 0
//                        }
//                        nextSongId = currentSongs[nextIndex].id
//                        nextName = currentSongs[nextIndex].songTitle
//                        nextAlbum = currentSongs[nextIndex].album
//                    }
//                    ManageSong.Previous -> {
//                        var previousIndex = i - 1
//                        if (previousIndex < 0) {
//                            previousIndex = Player.currentSongs.size - 1
//                        }
//                        nextSongId = currentSongs[previousIndex].id
//                        nextName = currentSongs[previousIndex].songTitle
//                        nextAlbum = currentSongs[previousIndex].album
//                    }
//                }
//
//            }
//        }
//        Player.currentId = nextSongId
//        Player.currentName = nextName
//        Player.currentAlbum = nextAlbum
//        if (Player.mediaPlayer?.isPlaying == true) {
//            Player.mediaPlayer?.stop()
//        }
//        Player.mediaPlayer?.reset()
//        Player.mediaPlayer = MediaPlayer.create(this, nextSongId)
//        Player.mediaPlayer?.start()
//        binding.songTitle.text = nextName
//        binding.albumPic.setImageResource(nextAlbum)
//    }
//
//    private fun play() {
//        Player.mediaPlayer?.apply {
//            if (!isPlaying) {
//                start()
//                binding.playbtn.setImageResource(R.drawable.pausebtn)
//            } else {
//                pause()
//                binding.playbtn.setImageResource(R.drawable.playbtn)
//            }
//        }
//    }
//}