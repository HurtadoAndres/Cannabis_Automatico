package com.example.cannabisautomatico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


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
        var dato_ideal = view.findViewById<TextView>(R.id.dato_ideal_r)
        var dato_cargando = view.findViewById<TextView>(R.id.dato_actual_r)

        dato_ideal.text= 45.toString()
        val texto = arguments?.getString("textFromActivityB")
        dato_cargando.text=texto

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
