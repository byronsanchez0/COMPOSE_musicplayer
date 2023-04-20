package com.example.musicplayer
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemSongBinding

class SongsAdapter(
    private val list: List<Pair<String, MediaPlayer>>
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    class SongViewHolder(binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        val view = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        binding.playbtn.setOnClickListener{
            val intent = Intent(SongList.On)
            // AQUIIIIIIIIIIIIIIIIIIIIIIII EL PUTEXTRA CON ID DEL ACANCION PA IR A SONGLIST QUE ES LA PANTALLA PARA REPRODUCIR
        }
        return SongViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.view.songTitle.text = list[position].first
    }
}