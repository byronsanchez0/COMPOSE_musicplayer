package com.example.musicplayer.ui.playerscreen

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding
import com.example.musicplayer.models.ManageSong
import com.example.musicplayer.models.Player

class MusicPlayerFragment : Fragment() {

    private lateinit var binding: FragmentMusicPlayerBinding
    lateinit var runnable: Runnable
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playbtn.setImageResource(R.drawable.pausebtn)
        binding.seekbar.progress = 0
        binding.seekbar.max = Player.mediaPlayer?.duration!!

        binding.playbtn.setOnClickListener {
            play()
        }

        binding.nextbtn.setOnClickListener {
            nextSong()
        }
        binding.previousbtn.setOnClickListener {
            previousSong()
        }

        binding.songListbtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.songTitle.text = Player.currentName
        binding.albumPic.setImageURI(Player.currentAlbum)

        //********SeekBar Management
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, change: Boolean) {
                if (change) {
                    Player.mediaPlayer?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        runnable = Runnable {
            binding.seekbar.progress = Player.mediaPlayer?.currentPosition ?: 0
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        Player.mediaPlayer?.setOnCompletionListener {
            binding.playbtn.setImageResource(R.drawable.pausebtn)
            binding.seekbar.progress = 0
        }
    }

    private fun nextSong() {
        changeSong(ManageSong.Next)
    }

    private fun previousSong() {
        changeSong(ManageSong.Previous)
    }

    private fun changeSong(manageSong: ManageSong) {
        var nextSongId : Uri? = null
        var nextName = ""
        var nextAlbum : Uri? = null
        val currentSongs = Player.currentSongs
        currentSongs.forEachIndexed { i, song ->
            if (Player.currentId == song.id) {
                when (manageSong) {
                    ManageSong.Next -> {
                        var nextIndex = i + 1
                        if (nextIndex > currentSongs.size - 1) {
                            nextIndex = 0
                        }
                        nextSongId = currentSongs[nextIndex].id
                        nextName = currentSongs[nextIndex].songTitle
                        nextAlbum = currentSongs[nextIndex].album
                    }
                    ManageSong.Previous -> {
                        var previousIndex = i - 1
                        if (previousIndex < 0) {
                            previousIndex = Player.currentSongs.size - 1
                        }
                        nextSongId = currentSongs[previousIndex].id
                        nextName = currentSongs[previousIndex].songTitle
                        nextAlbum = currentSongs[previousIndex].album
                    }
                }

            }
        }
        Player.currentId = nextSongId
        Player.currentName = nextName
        Player.currentAlbum = nextAlbum
        if (Player.mediaPlayer?.isPlaying == true) {
            Player.mediaPlayer?.stop()
        }
        Player.mediaPlayer?.reset()
        Player.mediaPlayer = MediaPlayer.create(requireContext(), nextSongId)
        Player.mediaPlayer?.start()
        binding.songTitle.text = nextName
        binding.albumPic.setImageURI(nextAlbum)
    }

    private fun play() {
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

}