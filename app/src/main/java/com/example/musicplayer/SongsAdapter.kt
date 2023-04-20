package com.example.musicplayer
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemSongBinding

class SongsAdapter(
    private val list: List<Pair<String, Int>>
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
        holder.view.songTitle.text = list[position].first
        holder.view.playbtn.setOnClickListener{
            val intent = Intent(it.context, MusicPlayer::class.java)
            intent.putExtra("songName", list[position].first)
            intent.putExtra("songId", list[position].second )
            it.context.startActivity(intent)
        }
    }
}