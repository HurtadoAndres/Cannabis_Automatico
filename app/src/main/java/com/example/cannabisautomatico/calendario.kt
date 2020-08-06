package com.example.cannabisautomatico

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker

class calendario : Fragment()  {

    lateinit var thiscontext : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendario, container, false)

        val builder : MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("seleccione fecha")
        val materialdatapicker: MaterialDatePicker<*> = builder.build()
        materialdatapicker.show(fragmentManager!!, materialdatapicker.toString())

        if (container != null) {
            thiscontext = container.context
        }


        return view
    }



    companion object {

        fun newInstance(param1: String, param2: String) =
            calendario().apply {

            }
    }
}
