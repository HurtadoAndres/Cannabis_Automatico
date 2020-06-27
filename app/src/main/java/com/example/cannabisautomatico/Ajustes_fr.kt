package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout


class Ajustes_fr : Fragment() {

    lateinit var btn_cuenta : LinearLayout
    lateinit var btn_notificaciones : LinearLayout
    lateinit var btn_acerca_de : LinearLayout
    lateinit var btn_atribuciones : LinearLayout
    lateinit var thiscontext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_ajustes_fr, container, false)
        btn_cuenta = view.findViewById(R.id.btn_cuenta)
        btn_notificaciones = view.findViewById(R.id.btn_notificacion)
        btn_acerca_de = view.findViewById(R.id.btn_acerca_de)
        btn_atribuciones = view.findViewById(R.id.btn_atribuciones)

        if (container != null) {
            thiscontext = container.context
        }


        btn_cuenta.setOnClickListener {
            val intent = Intent(thiscontext,pantalla_home_opciones::class.java)
            intent.putExtra("numero", 1)
            startActivity(intent)
        }

        btn_notificaciones.setOnClickListener {
            val intent = Intent(thiscontext,pantalla_home_opciones::class.java)
            intent.putExtra("numero", 3)
            startActivity(intent)
        }

        btn_acerca_de.setOnClickListener {
            startActivity(Intent(thiscontext,Acerca_de::class.java))
        }

        btn_atribuciones.setOnClickListener {
            startActivity(Intent(thiscontext,Atribuciones::class.java))
        }


        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            Ajustes_fr().apply {

            }
    }
}
