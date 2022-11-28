package com.miso.vinilos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.databinding.TrackFragmentBinding
import com.miso.vinilos.ui.adapters.TracksAdapter
import com.miso.vinilos.R
import com.miso.vinilos.models.Track
import com.miso.vinilos.viewmodels.TrackViewModel

class TrackFragment : Fragment() {
    private var _binding: TrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: TrackViewModel
    private var viewModelAdapter: TracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrackFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = TracksAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.commentsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_tracks)
        val args: TrackFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId)).get(TrackViewModel::class.java)
        viewModel.comments.observe(viewLifecycleOwner, Observer<List<Track>> {
            it.apply {
                viewModelAdapter!!.comments = this
                if(this.isEmpty()){
                    binding.txtNoComments.visibility = View.VISIBLE
                }else{
                    binding.txtNoComments.visibility = View.GONE
                }
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
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