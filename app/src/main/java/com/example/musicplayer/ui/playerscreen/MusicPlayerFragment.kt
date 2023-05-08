package com.example.musicplayer.ui.playerscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding
import com.example.musicplayer.models.ManageSong
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import com.example.musicplayer.ui.listscreen.ListViewModel
import com.example.musicplayer.ui.playerscreen.MusicPlayerViewModel.Companion.ALBUM_ART_URI
import com.example.musicplayer.ui.playerscreen.MusicPlayerViewModel.Companion.SONG_TITLE
import com.example.musicplayer.ui.playerscreen.MusicPlayerViewModel.Companion.SONG_URI
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MusicPlayerFragment : Fragment() {
    private val musicPlayerViewModel: MusicPlayerViewModel by viewModel()
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
        startObservables()
        binding.playbtn.setImageResource(R.drawable.pausebtn)
        binding.seekbar.progress = 0
        binding.seekbar.max = Player.mediaPlayer?.duration!!

        binding.playbtn.setOnClickListener {
            musicPlayerViewModel.play()
        }

        binding.nextbtn.setOnClickListener {
            musicPlayerViewModel.nextSong()
        }
        binding.previousbtn.setOnClickListener {
            musicPlayerViewModel.previousSong()
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

    private val songChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && intent.action == MusicPlayerViewModel.ACTION_SONG_CHANGED) {
                val songTitle = intent.getStringExtra(SONG_TITLE) ?: ""
                context?.let {
                    Snackbar.make(binding.root, songTitle, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(MusicPlayerViewModel.ACTION_SONG_CHANGED)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(songChangedReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(songChangedReceiver)
    }

    private fun startObservables() {
        musicPlayerViewModel.apply {
            title().observe(viewLifecycleOwner, Observer {
                binding.songTitle.text = it
            })
            album().observe(viewLifecycleOwner, Observer {
                binding.albumPic.setImageURI(it)
            })
            playBtn().observe(viewLifecycleOwner, Observer {
                binding.playbtn.setImageResource(it)
            })
        }
    }


}