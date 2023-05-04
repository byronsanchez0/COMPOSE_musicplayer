package com.example.musicplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.FragmentSongListBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song
import com.example.musicplayer.repository.SongsRepo

class ListFragment : Fragment(R.layout.fragment_song_list) {

    private lateinit var binding: FragmentSongListBinding
    val repo = SongsRepo()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSongListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Player.currentSongs = repo.allSongs
        binding.recyclerview.adapter = SongsAdapter(repo.allSongs)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())


    }

    companion object {
    }
}