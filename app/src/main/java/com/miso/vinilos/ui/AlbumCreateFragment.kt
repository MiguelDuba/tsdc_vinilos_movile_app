package com.miso.vinilos.ui

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.miso.vinilos.databinding.AlbumCreateFragmentBinding
import com.miso.vinilos.models.Album
import com.miso.vinilos.ui.dialogs.DateFragment
import com.miso.vinilos.viewmodels.AlbumCreateViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAlbumCreate.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumCreateFragment : Fragment() {
    lateinit var contexto:Context
    var generos = arrayOf("Classical","Salsa","Rock","Folk")
    var sellos = arrayOf("Sony Music","EMI","Discos Fuentes","Elektra","Fania Records")
    private val array_generos: Array<String> = arrayOf("Classical","Salsa","Rock","Folk")
    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel
    private lateinit var album: Album
    private lateinit var strAlbum: String

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val appContext = requireContext().applicationContext
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var aa = ArrayAdapter(this.contexto, android.R.layout.simple_spinner_item, generos)
        var aa2 = ArrayAdapter(this.contexto, android.R.layout.simple_spinner_item, sellos)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding.spinnergen)
        {
            adapter = aa
            setSelection(0, false)
            prompt = "Seleccione el Genero del Album"
            /**gravity = Gravity.CENTER
            onItemSelectedListener = this@FragmentAlbumCreate**/
        }
        with(binding.spinnersello)
        {
            adapter = aa2
            setSelection(0, false)
            prompt = "Seleccione el Sello Discografico"
            /**gravity = Gravity.CENTER
            onItemSelectedListener = this@FragmentAlbumCreate**/
        }
    }

    fun Int.twoDigits() =
        if (this <= 9) "0$this" else this.toString()

    private fun showDatePickerDialog() {
        binding.editTextFechaAlbum.setText("2022/01/01")
        val newFragment = DateFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = year.toString() + " / " + (month + 1).twoDigits() + " / " + day.twoDigits()
            binding.editTextFechaAlbum.setText(selectedDate)
        })

        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var i:Int=0
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Crear Album"
        viewModel = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application)).get(
            AlbumCreateViewModel::class.java)
        binding.editTextFechaAlbum.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnCrearAlbumRed.setOnClickListener{

            /** strAlbum="{\n    \"name\": \"El Cocinero Mayor\",\n    \"cover\": \"https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg\",\n    \"releaseDate\": \"1984-08-01T00:00:00-05:00\",\n    \"description\": \"Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.\",\n    \"genre\": \"Salsa\",\n    \"recordLabel\": \"Elektra\"\n}"  **/
            if (verifica()){
                strAlbum="{\n    \"name\": \""+binding.editTextNombreAlbum.text.toString()+"\",\n    \"cover\": \""+binding.editTextCoverUrl.text.toString()+"\",\n    \"releaseDate\": \""+binding.editTextFechaAlbum.text.toString()+"\",\n    \"description\": \""+binding.editTextDescripcion.text.toString()+"\",\n    \"genre\": \""+binding.spinnergen.selectedItem.toString()+"\",\n    \"recordLabel\": \""+binding.spinnersello.selectedItem.toString()+"\"\n}"
                lifecycleScope.launch{
                    val idAlbum=async{ viewModel.createAlbumFromNetwork(JSONObject(strAlbum)) }
                    i=idAlbum.await()
                }
                /** val action = FragmentAlbumCreateDirections.actionFragmentAlbumCreateToFragmentAlbumDetail(binding.txtEditNombreAlbum.text.toString(), binding.txtEditGeneroAlbum.text.toString(), binding.txtEditCoverAlbum.text.toString(), binding.txtEditFechaAlbum.text.toString(), binding.txtEditDescAlbum.text.toString())
                view?.findNavController()?.navigate(action) **/
            }
            else{
                Toast.makeText(activity, "Datos Invalidos", Toast.LENGTH_LONG).show()
            }

        }
        viewModel.album.observe(viewLifecycleOwner, /**binding. = this**/
            Observer<Album> {
                it.apply {
//                    val action = AlbumCreateFragmentDirections.actionFragmentAlbumCreateToAlbumDetailFragment(it.name, it.genre, it.cover, it.releaseDate, it.description, it.albumId)
                    val action = AlbumCreateFragmentDirections.actionFragmentAlbumCreateToAlbumDetailFragment(it.albumId)
                    view?.findNavController()?.navigate(action)
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

    fun verifica():Boolean{
        var resp: Boolean=true
        if (binding.editTextNombreAlbum.text.toString().isNullOrEmpty()){
            resp=false
        }
        else if(binding.editTextFechaAlbum.text.toString().isNullOrEmpty()){
            resp=false
        }
        else if(binding.editTextCoverUrl.text.toString().isNullOrEmpty()){
            resp=false
        }
        else if(binding.editTextDescripcion.text.toString().isNullOrEmpty()){
            resp=false
        }
        return resp
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentAlbumCreate.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlbumCreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}