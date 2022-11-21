package com.miso.vinilos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.databinding.AlbumDetailFragmentBinding
import com.miso.vinilos.databinding.AlbumDetailItemBinding
import com.miso.vinilos.databinding.TrackItemBinding
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Track
import com.miso.vinilos.ui.adapters.AlbumDetailAdapter
import com.miso.vinilos.ui.adapters.AlbumTracksAdapter
import com.miso.vinilos.viewmodels.AlbumDetailViewModel
import com.miso.vinilos.viewmodels.AlbumTracksViewModel

class AlbumDetailFragment : Fragment() {
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumDetailAdapter? = null

/*
    private var _trackBinding: AlbumDetailItemBinding? = null
    private val trackBinding get() = _trackBinding!!
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var viewModelTracks: AlbumTracksViewModel
    private var tracksModelAdapter: AlbumTracksAdapter? = null
*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumDetailAdapter()
        //tracksModelAdapter = AlbumTracksAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumDetailsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
/*
        trackRecyclerView = trackBinding.tracksAlbumRecycleView
        trackRecyclerView.layoutManager = LinearLayoutManager(context)
        trackRecyclerView.adapter = tracksModelAdapter
*/
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        activity.actionBar?.title = "Heyyy"
        val args:AlbumDetailFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, args.albumId)).get(
            AlbumDetailViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
                viewModelAdapter!!.album = this
//                if(this.isEmpty()){
//                    binding.txtNoComments.visibility = View.VISIBLE
//                }else{
//                    binding.txtNoComments.visibility = View.GONE
//                }
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
/*
        viewModelTracks = ViewModelProvider(this, AlbumTracksViewModel.Factory(activity.application, args.albumId)).get(
            AlbumTracksViewModel::class.java)
        viewModelTracks.tracks.observe(viewLifecycleOwner, Observer<List<Track>>{
            it.apply {
                tracksModelAdapter!!.tracks = this
            }
        })
        */
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}