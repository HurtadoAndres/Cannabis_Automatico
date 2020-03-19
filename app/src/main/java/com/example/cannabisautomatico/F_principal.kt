package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast



class F_principal : Fragment() {
    lateinit var modo_auto : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_f_principal, container, false)
        modo_auto = view.findViewById(R.id.M_auto)

        modo_auto.isChecked=true


        modo_auto.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){


            }
            Toast.makeText(activity,"Modo automatico activado", Toast.LENGTH_LONG).show()


        }



        return view
    }




    companion object {

        fun newInstance(param1: String, param2: String) =
            F_principal().apply {

            }
    }
}
