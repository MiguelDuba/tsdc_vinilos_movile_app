package com.miso.vinilos.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.miso.vinilos.R
import com.miso.vinilos.databinding.TrackItemBinding
import com.miso.vinilos.models.Track

class TracksAdapter: RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    var comments :List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val withDataBinding: TrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            TrackViewHolder.LAYOUT,
            parent,
            false)
        return TrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }


    class TrackViewHolder(val viewDataBinding: TrackItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.track_item
        }
    }

}