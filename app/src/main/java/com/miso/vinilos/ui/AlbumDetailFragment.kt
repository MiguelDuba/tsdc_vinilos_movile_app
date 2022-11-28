package com.miso.vinilos.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso.vinilos.databinding.AlbumDetailFragmentBinding
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
    private var albumId: Int = 0

    private lateinit var tracksRecyclerView: RecyclerView
    private var tracksAdapter: AlbumTracksAdapter? = null
    private lateinit var tracksViewModel: AlbumTracksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumDetailAdapter()
        tracksAdapter = AlbumTracksAdapter()
        val args: AlbumDetailFragmentArgs by navArgs()
        albumId = args.albumId
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumDetailsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        tracksRecyclerView = binding.trackRecycleView
        tracksRecyclerView.layoutManager = LinearLayoutManager(context)
        tracksRecyclerView.adapter = tracksAdapter
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        activity.actionBar?.title = "Heyyy"
        viewModel =
            ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, albumId))
                .get(AlbumDetailViewModel::class.java)
        tracksViewModel =
            ViewModelProvider(this, AlbumTracksViewModel.Factory(activity.application, albumId))
                .get(AlbumTracksViewModel::class.java)

        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
                viewModelAdapter!!.album = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        tracksViewModel.tracks.observe(viewLifecycleOwner, Observer<List<Track>>{
            it.apply {
                if(this.size == 0){
                    tracksAdapter!!.tracks= listOf<>(Track(0, "Este album no tiene canciones disponibles", ""))
                }else{
                    tracksAdapter!!.tracks = this
                }
            }
        })

        tracksViewModel.eventNetworkError.observe(viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}