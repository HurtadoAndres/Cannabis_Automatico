package com.example.cannabisautomatico

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*


class historial_fr : Fragment() {

    var id_h: String ? = null
    var fecha_h : String ? = null
    var hora_h : String ? = null
    var accion_h : String ? = null

    lateinit var txtf_h : TextView
    lateinit var txtcontenido : TextView
    lateinit var  btn_eliminar: LinearLayout

    lateinit var thiscontext :Context
    lateinit  var contenedorP : ViewGroup

    var KEY_CODIGO = "codigo"
    var URL_ELIMINAR_H = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/eliminar_historial.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            id_h = arguments?.getString("id")
            fecha_h = arguments?.getString("fecha")
            hora_h = arguments?.getString("hora")
            accion_h = arguments?.getString("accion")

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_historial_fr, container, false)
        txtf_h = view.findViewById(R.id.f_h)
        txtcontenido = view.findViewById(R.id.contenido)
        btn_eliminar = view.findViewById(R.id.btn_eliminar)
        contenedorP = view.findViewById(R.id.container)

        txtf_h.setText("$hora_h - $fecha_h")
        txtcontenido.setText("$accion_h")

        if (container != null) {
            thiscontext = container.context
        }



        btn_eliminar.setOnClickListener {
            TransitionManager.beginDelayedTransition(contenedorP)
            alerta()
        }


        return view
    }

    fun alerta(): AlertDialog? {
        val builder = AlertDialog.Builder(thiscontext)
            .setMessage("Estas seguro en " +
                    "querer eliminar este  \n" +
                    "historial")
            .setCancelable(false)

            .setPositiveButton("OK") { dialog, which ->
                eliminarHistorial()
            }
            .setNegativeButton("CANCELAR"){dialog, which -> dialog.cancel() }

        builder.setTitle("Eliminar historial").show()
        return builder.create()
    }


    fun eliminarHistorial() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_ELIMINAR_H,
            Response.Listener { response ->
                val intent = Intent(thiscontext, HomeActivity::class.java)
                intent.putExtra("pagina_fr", 3)
                startActivity(intent)
                activity?.finish()
            }, Response.ErrorListener { error ->
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] = id_h.toString()


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            historial_fr().apply {

            }
    }
}