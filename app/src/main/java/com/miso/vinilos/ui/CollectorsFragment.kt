package com.miso.vinilos.ui

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
import com.miso.vinilos.ui.adapters.CollectorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.miso.vinilos.databinding.AlbumFragmentBinding
import com.miso.vinilos.databinding.ArtistFragmentBinding
import com.miso.vinilos.databinding.CollectorFragmentBinding
import com.miso.vinilos.databinding.CollectorItemBinding
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.adapters.ArtistsAdapter
import com.miso.vinilos.viewmodels.AlbumViewModel
import com.miso.vinilos.viewmodels.CollectorViewModel


class CollectorsFragment : Fragment() {

    private var dataBinding : CollectorFragmentBinding? = null
    private val binding get() = dataBinding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = CollectorFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application)).get(CollectorViewModel::class.java)
        viewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>>{
            it.apply {
                viewModelAdapter!!.collectors = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean>{
                isNetworkError -> if(isNetworkError) onNetworkError()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}