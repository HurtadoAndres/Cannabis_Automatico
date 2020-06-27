package com.example.cannabisautomatico

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.getbase.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class historial : Fragment() {


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

    lateinit var  btn_eliminar: FloatingActionButton

    var idArray : ArrayList<String> = ArrayList()
    var fechaArray : ArrayList<String> = ArrayList()
    var horaArray : ArrayList<String> = ArrayList()
    var accionesArray : ArrayList<String> = ArrayList()

    var KEY_CODIGO = "codigo"
    var URL_ELIMINAR_H = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/eliminar_historial.php"


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
        btn_eliminar = view.findViewById(R.id.btn_eliminar)
        imagen_sinConexion.visibility = (View.INVISIBLE)
        mensaje_cone.visibility = (View.INVISIBLE)
        if (container != null) {
            thiscontext = container.context
        }

        cargar_UusarioPassword()
        is_Hay_Internet()


        contactList = ArrayList<Any>()

        btn_eliminar.setOnClickListener {
            alerta()
        }



        lv.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            var id = idArray[position]
            var fecha = fechaArray[position]
            var hora = horaArray[position]
            var accion = accionesArray[position]

            var numero :Int = 2

            var intent = Intent(thiscontext,pantalla_home_opciones::class.java)
            intent.putExtra("id", id)
            intent.putExtra("fecha", fecha)
            intent.putExtra("hora", hora)
            intent.putExtra("accion", accion)
            intent.putExtra("numero", numero)
            startActivity(intent)

        }


        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            historial().apply {

            }
    }

    fun alerta(): AlertDialog? {
        val builder = AlertDialog.Builder(thiscontext)
            .setMessage("Estas seguro en " +
                    "querer eliminar este  \n" +
                    "historial")
            .setCancelable(false)

            .setPositiveButton("OK") { dialog, which ->
                var id_h :String
                for (i in 0 until idArray.size){
                    id_h = idArray[i]
                    eliminarHistorial(id_h)
                }

            }
            .setNegativeButton("CANCELAR"){dialog, which -> dialog.cancel() }

        builder.setTitle("Eliminar historial").show()
        return builder.create()
    }

    fun eliminarHistorial(id_h:String) {
        fondo_cargando.visibility = (View.VISIBLE)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_ELIMINAR_H,
            Response.Listener { response ->
                fondo_cargando.visibility = (View.INVISIBLE)
                val intent = Intent(thiscontext, HomeActivity::class.java)
                intent.putExtra("pagina_fr", 3)
                startActivity(intent)
                activity?.finish()
            }, Response.ErrorListener { error ->
                fondo_cargando.visibility = (View.INVISIBLE)
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] = id_h


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

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
                        idArray.add(idJ)
                        fechaArray.add(fechaJ)
                        horaArray.add(horaJ)
                        accionesArray.add(accionesJ)
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
