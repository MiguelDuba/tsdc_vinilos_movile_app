package com.miso.vinilos.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.R
import com.miso.vinilos.models.Collector
import com.miso.vinilos.ui.adapters.CollectorViewAdapter
import androidx.lifecycle.Observer
import com.miso.vinilos.viewmodels.CollectorViewModel


class CollectorsFragment : Fragment(R.layout.fragment_artists) {

    private lateinit var viewModel: CollectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var collectorFragment = inflater.inflate(R.layout.collector_fragment, container, false)
        var collectorRecyclerView = collectorFragment.findViewById<RecyclerView>(R.id.collectors_recycler_view)
        var adapter = CollectorViewAdapter()
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(viewModelStore, CollectorViewModel.Factory(activity.application))[CollectorViewModel::class.java]
        viewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>>{
            it.apply {
                adapter!!.collectors = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean>{
            isNetworkError -> if(isNetworkError) onNetworkError()
        })
        collectorRecyclerView.layoutManager = GridLayoutManager(context, 3)
        collectorRecyclerView.adapter = CollectorViewAdapter()
        return collectorFragment
    }


    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}