package com.miso.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.R
import com.miso.vinilos.models.Collector

class CollectorViewAdapter() :
    RecyclerView.Adapter<CollectorViewAdapter.ViewHolder>() {

     var collectors: MutableList<Collector> = mutableListOf()
         set(value) {
             field = value
             this.notifyDataSetChanged()
         }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.collector_name)
            imageView = view.findViewById(R.id.collector_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.collector_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = collectors[position].name
        holder.imageView.setImageResource(R.drawable.ic_collectors)
    }

    override fun getItemCount(): Int {
        return collectors.size
    }


}