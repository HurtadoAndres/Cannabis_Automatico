package com.example.cannabisautomatico

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import java.util.*

class cuenta_fr : Fragment() {
    lateinit var btn_guardar_usu : Switch
    lateinit var  btn_cambiar_clave : Button
    var random = Random()

    var check : Boolean = false
    lateinit var thiscontext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view :View = inflater.inflate(R.layout.fragment_cuenta_fr, container, false)
        btn_guardar_usu = view.findViewById(R.id.gusuario)
        btn_cambiar_clave = view.findViewById(R.id.btn_canbiar_clave)

        if (container != null) {
            thiscontext = container.context
        }


        btn_guardar_usu.isChecked = obtenerEstado()
        btn_guardar_usu.setOnClickListener {
            if (btn_guardar_usu.isChecked){
                check = true
                estadoUsu(check)
            }else{
                check = false
                estadoUsu(check)
            }
        }


        btn_cambiar_clave.setOnClickListener {
            alerta()
        }

        return view
    }

    fun alerta(): AlertDialog? {
        val builder = AlertDialog.Builder(thiscontext)
            .setMessage("Estas seguro en querer cambiar \n" +
                    "la clave de tu usuario ")
            .setCancelable(false)

            .setPositiveButton("SI") { dialog, which ->
                var numero = (1000 + random.nextInt((9999) - 1000))

                val intet =Intent(thiscontext,verificacion_codigo_cambio_clave::class.java)
                intet.putExtra("codigo",numero)
                intet.putExtra("pagina",1)
                startActivity(intet)

            }
            .setNegativeButton("NO"){dialog, which -> dialog.cancel() }

        builder.setTitle("Activar cuenta").show()
        return builder.create()
    }

    fun estadoUsu(estado:Boolean){
        var preferenfs= thiscontext.getSharedPreferences("estado_usu", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putBoolean("estado", estado)
        editor.apply()
    }

    fun obtenerEstado(): Boolean {
        var preferenfs= thiscontext.getSharedPreferences("estado_usu", Context.MODE_PRIVATE)
        return  preferenfs.getBoolean("estado", false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cuenta_fr().apply {

            }
    }
}