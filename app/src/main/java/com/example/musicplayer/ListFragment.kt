package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.FragmentSongListBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import com.example.musicplayer.repository.SongsRepo

class ListFragment : Fragment(R.layout.fragment_song_list) {
    private var defaultSongs: List<Song> = listOf()

    private val viewmodel: ListViewModel by viewModels()
//    private val sharedViewModel: SettingScreenViewModel by activityViewModels {
//        CustomViewModelFactory(SongRepository)
//    }
    private var currentSongIndex: Int = 0
    private var songs: MutableList<Song> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentSongListBinding? = null
    private val binding get() = _binding!!

//    private lateinit var binding: FragmentSongListBinding
//    val repo = SongsRepo()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSongListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewmodel.loadSongsFromProvider(requireActivity().contentResolver)
        defaultSongs = songRepository.getDefaultSongs()

        println(defaultSongs)
//        sharedViewModel.songs.observe(viewLifecycleOwner) { newSongs ->
//            songs = newSongs.toMutableList()
//            setupRecyclerView()
//        }

        // Observe the deleted song position
//        sharedViewModel.deletedSongPosition.observe(viewLifecycleOwner) { position ->
//            position?.let {
//                // Remove the song from the local list
//                songs.removeAt(it)
//                recyclerView.adapter?.notifyItemRemoved(it)
//            }
//        }


    }
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val adapter = SongsAdapter(songs, this::onSongClick)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun onSongClick(position: Int) {
        playSelectedSong(position)
//        navigateToDetailActivity(position)
    }

    private fun initViews() {
        recyclerView = binding.recyclerview

        binding.playInorderButton.setOnClickListener {
            playPlaylist()
        }

        binding.playRandomButton.setOnClickListener {
            toggleRandomStart()
        }

//        binding.settingsButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homeScreenFragment_to_settingScreenFragment)
//        }
    }

    private fun playPlaylist() {
        if (songs.isNotEmpty()) {
            currentSongIndex = 0 // Always set the index to the first song
            playSelectedSong(currentSongIndex)
//            navigateToDetailActivity(currentSongIndex)
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_songs_in_the_playlist),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun toggleRandomStart() {
        if (songs.isNotEmpty()) {
            currentSongIndex = (0 until songs.size).random()
            playSelectedSong(currentSongIndex)
//            navigateToDetailActivity(currentSongIndex)
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_songs_in_the_playlist),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun playSelectedSong(position: Int) {
        Player.mediaPlayer?.release()
        currentSongIndex = position
        Player.mediaPlayer = MediaPlayer.create(context, songs[position].id)
        Player.mediaPlayer?.start()
    }



    companion object {
    }
}