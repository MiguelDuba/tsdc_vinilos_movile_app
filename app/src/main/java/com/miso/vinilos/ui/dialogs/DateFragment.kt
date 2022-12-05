package com.miso.vinilos.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class DateFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), listener, year, month, day)
    }

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DateFragment {
            val fragment = DateFragment()
            fragment.listener = listener
            return fragment
        }
    }

}