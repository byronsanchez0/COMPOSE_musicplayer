package com.example.musicplayer.ui.settingsscreen

import SettingsAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentSettingsBinding
import com.example.musicplayer.models.Song
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservables()
        settingsViewModel.getAllSongs()

    }

    private fun startObservables() {
        settingsViewModel.allSongs().observe(viewLifecycleOwner, Observer { songs ->
            setupRecyclerView(songs)
        })
        settingsViewModel.message().observe(viewLifecycleOwner, Observer{message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        })
    }
    private fun setupRecyclerView(songs: List<Song>) {
        val layoutManager = LinearLayoutManager(context)
        binding.settingsRecyclv.layoutManager = layoutManager

        val adapter = SettingsAdapter(songs, this::onSongClick)
        binding.settingsRecyclv.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.settingsRecyclv.context,
            layoutManager.orientation
        )
        binding.settingsRecyclv.addItemDecoration(dividerItemDecoration)
    }

    private fun onSongClick(song: Song) {
        settingsViewModel.addSongtoList(song)
    }

}