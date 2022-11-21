package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.R
import com.miso.vinilos.databinding.CollectorItemBinding
import com.miso.vinilos.models.Collector

class CollectorAdapter :
    RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    var collectors: List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val dataBinding : CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false
        )
        return CollectorViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.collectorItemBinding.also {
            it.collector = collectors[position]
            it.collectorImage.setImageResource(R.drawable.ic_collectors)
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    class CollectorViewHolder(val collectorItemBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(collectorItemBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }

}