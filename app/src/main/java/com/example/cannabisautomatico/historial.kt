package com.example.cannabisautomatico

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

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
    lateinit var thiscontext : Context
    lateinit var fondo_cargando: LottieAnimationView
    lateinit var nodata : TextView
    lateinit var imagen_sinConexion : ImageView
    lateinit var mensaje_cone : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_historial, container, false)

        nodata = view.findViewById<TextView>(R.id.Nodata)
        lv=view.findViewById(R.id.HistorialList)
        fondo_cargando = view.findViewById(R.id.fondo_cargando)
        imagen_sinConexion = view.findViewById(R.id.sin_conexion)
        mensaje_cone = view.findViewById(R.id.mensaje_conexion)
        imagen_sinConexion.visibility = (View.INVISIBLE)
        mensaje_cone.visibility = (View.INVISIBLE)
        if (container != null) {
            thiscontext = container.context
        }

        cargar_UusarioPassword()
        is_Hay_Internet()


        contactList = ArrayList<Any>()





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
        fondo_cargando.visibility=(View.VISIBLE)
        var a:Int=0
        var response =  Response.Listener<String> { response ->
            val json = JSONObject(response)
            var success = json.getBoolean("success")
            if (json.length()==1){
                fondo_cargando.visibility=(View.INVISIBLE)
                nodata.visibility=(View.VISIBLE)
            }

            if (success) {

            } else {

                try {
                    val jsonarray = json.getJSONArray("historial")
                    for (i in 0 until jsonarray.length()) {
                        a++
                        var jsonA = jsonarray.getJSONObject(i)
                        idJ = jsonA.getString("codigo")
                        fechaJ = jsonA.getString("fecha")
                        horaJ = jsonA.getString("hora")
                        accionesJ = jsonA.getString("acciones")
                        // contactList.add("Id : $idJ\nHora : $horaJ\nFecha : $fechaJ\nAcciones= $accionesJ")
                        (mlista as ArrayList<HistorialCl>).add(
                            HistorialCl(
                                horaJ,
                                fechaJ
                            )
                        )
                    }
                    if (a==jsonarray.length()){fondo_cargando.visibility=(View.INVISIBLE)}

                    cargarLisview()


                } catch (e: JSONException) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show()
                }


            }

        }

        var registro = HistorialRequest(usuarioId,response)
        var queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(registro)


    }

    fun cargarLisview(){
        try {
            madapter =  ListaAdapter(context!!,R.layout.prest_historial, mlista as ArrayList<HistorialCl>)
            lv.adapter=madapter
        }catch (e:Exception){e.printStackTrace()}

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun is_Hay_Internet(){
        val connectivityManager =
            thiscontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities: NetworkCapabilities? =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)) {
                imagen_sinConexion.visibility = (View.INVISIBLE)
                mensaje_cone.visibility = (View.INVISIBLE)
                consultaHistorial()
            }
        }else{
            imagen_sinConexion.visibility = (View.VISIBLE)
            mensaje_cone.visibility = (View.VISIBLE)
            fondo_cargando.visibility=(View.INVISIBLE)
            Toast.makeText(thiscontext,"Nose puede conectar, verifique el acceso a internet e Intente nuevamente",Toast.LENGTH_LONG).show()

        }
    }

}
