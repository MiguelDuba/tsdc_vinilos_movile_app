package com.miso.vinilos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.navArgs
import com.miso.vinilos.models.Artist
//import androidx.navigation.fragment.findNavController
import com.miso.vinilos.R
import com.miso.vinilos.databinding.ArtistDetailFragmentBinding
import com.miso.vinilos.ui.adapters.ArtistDetailAdapter
import com.miso.vinilos.viewmodels.ArtistDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArtistDetailFragment : Fragment() {
    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistDetailViewModel
    private var viewModelAdapter: ArtistDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistDetailRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
//        activity.actionBar?.title = getString(R.string.title_comments)

        activity.actionBar?.title = "Heyyy"
        val args: ArtistDetailFragmentArgs by navArgs()
//        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application, args.artistId)).get(ArtistDetailViewModel::class.java)
        viewModel.artist.observe(viewLifecycleOwner, Observer<Artist> {
            it.apply {
                viewModelAdapter!!.artists = this
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