package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class historial : Fragment() {

    lateinit var  btn_home: Button
    var contactList = ArrayList<Any>()
    lateinit var lv: ListView
    var usuarioId:String=""

    var idJ:String=""
    var fechaJ:String=""
    var horaJ:String=""
    var accionesJ:String=""

    var mlista : List<HistorialCl> = ArrayList()
    lateinit var madapter : ListaAdapter
    private lateinit var  handler : Handler

    lateinit var fondo_cargando: LinearLayout
    lateinit var nodata : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_historial, container, false)

        nodata = view.findViewById<TextView>(R.id.Nodata)
        lv=view.findViewById(R.id.HistorialList)
        fondo_cargando = view.findViewById(R.id.fondo_cargando)
        consultaHistorial()

        contactList = ArrayList<Any>()
        fondo_cargando.visibility=(View.VISIBLE)

        handler = Handler()
        handler.postDelayed({
            fondo_cargando.visibility=(View.INVISIBLE)

        }, 3000)
        if (mlista.isNotEmpty()){
            nodata.visibility =(View.VISIBLE)}


        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            historial().apply {

            }
    }


    fun cargar_UusarioPassword(){
        var preferenfs= this.activity?.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var usuario : String? = preferenfs?.getString("email", "")
        usuarioId=usuario.toString()




    }

    fun consultaHistorial(){
        var response =  Response.Listener<String> { response ->
            val json = JSONObject(response)
            var success = json.getBoolean("success")
            if (json.length()==1){
                nodata.visibility=(View.VISIBLE)
            }

            if (success) {

            } else {

                try {
                    val jsonarray = json.getJSONArray("historial")

                    for (i in 0 until jsonarray.length()) {
                        var jsonA = jsonarray.getJSONObject(i)
                        idJ = jsonA.getString("codigo")
                        fechaJ = jsonA.getString("fecha")
                        horaJ = jsonA.getString("hora")
                        accionesJ = jsonA.getString("acciones")
                        // contactList.add("Id : $idJ\nHora : $horaJ\nFecha : $fechaJ\nAcciones= $accionesJ")
                        (mlista as ArrayList<HistorialCl>).add(
                            HistorialCl(
                                idJ,
                                horaJ,
                                fechaJ
                            )
                        )
                    }

                    cargarLisview()



                } catch (e: JSONException) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show()
                }
            }

        }
        cargar_UusarioPassword()
        var registro = HistorialRequest(usuarioId,response)
        var queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(registro)


    }

    fun cargarLisview(){
        // var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
        // lv.adapter=adapter

        madapter =  ListaAdapter(context!!,R.layout.prest_historial, mlista as ArrayList<HistorialCl>)
        lv.adapter=madapter
    }

}
