package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.R
import com.miso.vinilos.databinding.AlbumDetailItemBinding
import com.miso.vinilos.databinding.TrackItemBinding
import com.miso.vinilos.models.Track

class AlbumTracksAdapter : RecyclerView.Adapter<AlbumTracksAdapter.AlbumTracksViewHolder>() {

    var tracks: List<Track> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        val withDataBinding: TrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailAdapter.AlbumDetailViewHolder.LAYOUT,
            parent,
            false)
        return AlbumTracksViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTracksViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.index.text = (position + 1).toString()
            it.track = tracks[position]
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }



    class AlbumTracksViewHolder(val viewDataBinding: TrackItemBinding):
            RecyclerView.ViewHolder(viewDataBinding.root){
                companion object
                @LayoutRes
                val LAYOUT = R.layout.track_item
            }
}