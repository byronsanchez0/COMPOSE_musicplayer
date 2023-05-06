package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.databinding.ItemSongBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song

@Suppress("DEPRECATION")

class SongsAdapter(
    private val list: List<Song>,
    private val onSongClickListener: (Int) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    inner class SongViewHolder(binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.songTitle
        val image : ImageView = binding.albumImg
//        val view = binding

        init{
            binding.root.setOnClickListener{
                onSongClickListener(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = list[position]
        holder.title.text = song.songTitle
        Glide.with(holder.image.context).load(song.album).into(holder.image)
        }





}