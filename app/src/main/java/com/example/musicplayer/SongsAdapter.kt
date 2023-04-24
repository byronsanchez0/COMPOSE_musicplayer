package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemSongBinding
import com.example.musicplayer.models.Player
import com.example.musicplayer.models.Song

class SongsAdapter(
    private val list: List<Song>
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    class SongViewHolder(binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        val view = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.view.songTitle.text = list[position].songTitle
        holder.view.playbtn.setOnClickListener {
            val intent = Intent(it.context, MusicPlayer::class.java)

            Player.currentName = list[position].songTitle
            Player.currentId = list[position].id
            Player.currentAlbum = list[position].album

            Player.mediaPlayer?.release()
            Player.mediaPlayer = MediaPlayer.create(it.context, list[position].id)
            Player.mediaPlayer?.start()
            it.context.startActivity(intent)
        }
    }
}