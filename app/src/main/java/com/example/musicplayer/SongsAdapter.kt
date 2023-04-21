package com.example.musicplayer
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemSongBinding

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
        holder.view.playbtn.setOnClickListener{
            val intent = Intent(it.context, MusicPlayer::class.java)
            intent.putExtra("songName", list[position].songTitle)
            intent.putExtra("songId", list[position].id )
            intent.putExtra("songAlbum", list[position].album)
            it.context.startActivity(intent)
        }
    }
}