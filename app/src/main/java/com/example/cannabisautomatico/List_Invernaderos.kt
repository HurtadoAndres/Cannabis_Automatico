package com.example.cannabisautomatico



import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class List_Invernaderos() : Fragment() {

    lateinit var btn_mas_invernadero: FloatingActionButton
    var expandablelistview : ExpandableListView? = null
    var expanablelistAdapter : ExpandableListAdapter?=null
    lateinit var expandableTITULO: ArrayList<String>
    lateinit var listaDETALLE  : HashMap<String, Invernadero>
    var expandablePosition :Int = -1

    var titulo : String = ""
    var titulo_s : String = ""
    var descripcion : String = ""
    var ruta_imagen : String = ""
    var email : String = ""
    var codigo : String = ""
    var urlImagen : String = ""
    var KEY_EMAIL = "email"
    lateinit var request : RequestQueue
    lateinit var thiscontext : Context
    var seleccionado : Boolean = false
   lateinit var menu :LinearLayout
    lateinit  var contenedorP : ViewGroup


    //ObjectAnimator -> Nos proporciona soporte parar animar nuestros objetos
    private var animatorX: ObjectAnimator? = null
    private var animatorY: ObjectAnimator? = null
    private var animatorAlpha: ObjectAnimator? = null
    private var animatorRotation: ObjectAnimator? = null
    private val animatorAll: ObjectAnimator? = null

    private val animationDuration: Long = 1000
    lateinit var imagen_sinConexion : ImageView
    lateinit var mensaje_cone : TextView
    //AnimatorSet -> Reproduce un conjunto de ObjectAnimator en un orden especificado. Las animaciones pueden ser todas a la vez o secuenciadas
    private val animatorSet: AnimatorSet? = null

    lateinit var eliminar : LinearLayout
    lateinit var editar : LinearLayout
    lateinit var cancelar : Button


    var tituloArray : ArrayList<String> = ArrayList()
    var titulo_sArray : ArrayList<String> = ArrayList()
    var descripcionArray : ArrayList<String> = ArrayList()
    var idArray : ArrayList<String> = ArrayList()
    var rutaImagenArray : ArrayList<String> = ArrayList()

    var posicionCheck: ArrayList<String> = ArrayList()

    var t : String = ""
    var t_s : String = ""
    var d : String = ""
    var i : String = ""
    var r : String = ""

    var posicion :Int = 0
    var KEY_CODIGO = "codigo"
    var URL_ELIMINAR = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/eliminar_campo.php"
    var URL_VERACTIVADO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/estadoActivadoInvernadero.php"
    var KEY_ESTADO="estado"

    lateinit var activar : LinearLayout
    var activar_opcion : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_list__invernaderos, container, false)
        btn_mas_invernadero = view.findViewById(R.id.mas_invernadero)
        request = Volley.newRequestQueue(activity)
        this.expandablelistview = view.findViewById(R.id.inverdaero)
        menu = view.findViewById(R.id.menu_select)
        contenedorP = view.findViewById(R.id.container)
        imagen_sinConexion = view.findViewById(R.id.sin_conexion)
        mensaje_cone = view.findViewById(R.id.mensaje_conexion)
        imagen_sinConexion.visibility = (View.INVISIBLE)
        mensaje_cone.visibility = (View.INVISIBLE)

        eliminar = view.findViewById(R.id.eliminar)
        cancelar = view.findViewById(R.id.cancelar)
        editar = view.findViewById(R.id.editar)

        activar = view.findViewById(R.id.activar)

        expandableTITULO = ArrayList()
        listaDETALLE = HashMap()


        if (container != null) {
            thiscontext = container.context
        }


        //checkNetworkConnectionStatus()
        is_Hay_Internet()

      btn_mas_invernadero.setOnClickListener {
            startActivity(Intent(activity,add_invernadero_Activity::class.java))

        }


        editar.setOnClickListener {
            editar()
        }

        eliminar.setOnClickListener {
            for (i in 0 until posicionCheck.size){
                eliminarInvernadero(posicionCheck[i].toInt())
            }

        }

        cancelar.setOnClickListener {
            startActivity(Intent(activity,HomeActivity::class.java))
        }


        //opcion de activar invernadero para poder tomar la fecha la cual es importante para las etapas del cultivo
        activar.setOnClickListener {

            activar_opcion = !activar_opcion

            if (activar_opcion) {
                activar.background = thiscontext.getDrawable(R.drawable.fondo_btn_s)
                opcion_VerInvernaderoActivado(posicion,"true")
                val toast =
                    Toast.makeText(thiscontext, "Invernadero activado", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

            }else{
                activar.background = thiscontext.getDrawable(R.drawable.btn_btn_final)
                opcion_VerInvernaderoActivado(posicion,"false")
                val toast =
                    Toast.makeText(thiscontext, "Invernadero desativado", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
            }

        }


        return view
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun is_Hay_Internet(){
        val connectivityManager =
            thiscontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities: NetworkCapabilities? =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                imagen_sinConexion.visibility = (View.INVISIBLE)
                mensaje_cone.visibility = (View.INVISIBLE)
                insertDatosInfropmacion()
            }
        }else{
            imagen_sinConexion.visibility = (View.VISIBLE)
            mensaje_cone.visibility = (View.VISIBLE)
            Toast.makeText(thiscontext,"Nose puede conectar, verifique el acceso a internet e Intente nuevamente",Toast.LENGTH_LONG).show()

        }
    }

    fun metodoAtador(){

        this.expandableTITULO = ArrayList(listaDETALLE.keys)

        expandablelistview?.setOnGroupExpandListener { position ->

            if (expandablePosition != -1 && position != expandablePosition){
                expandablelistview?.collapseGroup(expandablePosition)
            }
            expandablePosition = position


        }



        expandablelistview?.onItemLongClickListener = AdapterView.OnItemLongClickListener { av, v, pos, id ->
            // TransitionManager.beginDelayedTransition(contenedorP)
            var check = v.findViewById<CheckBox>(R.id.seleccionado)
            seleccionado = !seleccionado
            posicion = pos


            animacion("alpha")
            btn_mas_invernadero.visibility = (View.INVISIBLE)
            menu.visibility = (View.VISIBLE)
            check.visibility = (View.VISIBLE)

            check.isChecked = true
            posicionCheck.add(pos.toString())

            check.setOnClickListener {


                if (check.isChecked){
                    posicionCheck.add(pos.toString())
                }else{
                    posicionCheck.remove(pos.toString())
                }

                if ( posicionCheck.isEmpty()){
                    TransitionManager.beginDelayedTransition(contenedorP)
                    btn_mas_invernadero.visibility = (View.VISIBLE)
                    menu.visibility = (View.INVISIBLE)
                    check.visibility = (View.INVISIBLE)

                  ///  Toast.makeText(thiscontext,"$pos",Toast.LENGTH_LONG).show()
                }
            }





            true
        }




        expandablelistview?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val fr1= Home_fr()
            val args = Bundle()
            args.putString("id", idArray[groupPosition])
            fr1.setArguments(args)
            val trantition1 : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            trantition1.replace(R.id.contenedor, fr1)
            trantition1.commit()
            guardarInvernaderoClick(groupPosition)
            activity?.Descripcion?.text= resources.getText(R.string.Info_sensores)
            if (activity?.Descripcion?.text == "Sensores"){
                activity?.tablero_info?.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
                activity?.Descripcion!!.setPadding(140,0,0,0)
                activity?.Titulop?.setPadding(140,0,0,0)
                activity?.btn_atras?.visibility=(View.VISIBLE)
            }


            false

        }


            this.expanablelistAdapter = Adapter_invernadero(thiscontext, expandableTITULO, listaDETALLE)
            expandablelistview?.setAdapter(this.expanablelistAdapter)


    }

    fun editar(){
        var position  = posicion
        t = tituloArray[position]
        t_s = titulo_sArray[position]
        d = descripcionArray[position]
        i = idArray[position]
        r = rutaImagenArray[position]
        var numero :Int = 0

        var intent = Intent(thiscontext,pantalla_home_opciones::class.java)
        intent.putExtra("id_e", i)
        intent.putExtra("ti_e", t)
        intent.putExtra("ti_s_e", t_s)
        intent.putExtra("des_e", d)
        intent.putExtra("rutaIMG_e", r)
        intent.putExtra("numero", numero)
        startActivity(intent)

    }


    private fun animacion(animacion: String) {
        when (animacion) {
            "alpha" -> {
                animatorAlpha = ObjectAnimator.ofFloat(menu, View.ALPHA, 0.0f, 1.0f)
                animatorAlpha?.duration = animationDuration
                val animatorSetAlpha = AnimatorSet()
                animatorSetAlpha.playTogether(animatorAlpha)
                animatorSetAlpha.start()
            }
            else -> {
            }
        }
    }


    fun insertDatosInfropmacion() {
        var URLDATOS = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/invernaderoTraer.php"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URLDATOS,
            Response.Listener { response ->
                val json = JSONObject(response)
                try {
                    val jsonarray = json.getJSONArray("imagen")

                    for (i in 0 until jsonarray.length()) {

                        var jsonA = jsonarray.getJSONObject(i)
                        codigo = jsonA.getString("codigo")
                        titulo= jsonA.getString("nombre")
                        titulo_s = jsonA.getString("titulo")
                        descripcion = jsonA.getString("descripcion")
                        ruta_imagen = jsonA.getString("imagen_ruta")
                        email=obtenerEmail().toString()

                        urlImagen= "https://cannabisautomatico.000webhostapp.com/ServicioWeb/$ruta_imagen"

                        this.listaDETALLE [i.toString()] = Invernadero(
                            titulo, titulo_s, descripcion, urlImagen
                        )
                        metodoAtador()

                        idArray.add(codigo)
                        tituloArray.add(titulo)
                        titulo_sArray.add(titulo_s)
                        descripcionArray.add(descripcion)
                        rutaImagenArray.add(urlImagen)
                    }

                } catch (e: JSONException) {
                    Toast.makeText(thiscontext, "" + e, Toast.LENGTH_LONG).show()
                }

            }, Response.ErrorListener { error ->
                Toast.makeText(activity, error.message.toString(), Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var email:String = obtenerEmail().toString()

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = email

                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)


    }

    fun eliminarInvernadero(posicion_r:Int) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_ELIMINAR,
            Response.Listener { response ->
                startActivity(Intent(activity,HomeActivity::class.java))
            }, Response.ErrorListener { error ->
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] = idArray[posicion_r]


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }

    fun opcion_VerInvernaderoActivado(posicion_r:Int, estado:String) {
        val stringRequest: StringRequest = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : StringRequest(
            Method.POST, URL_VERACTIVADO,
            Response.Listener { response ->
                val json = JSONObject(response)
                var getActivado= json.getString("getactivado")
                if(getActivado == "true") {
                    activar.background = thiscontext.getDrawable(R.drawable.fondo_btn_s)
                    activar_opcion = true
                }
                else{
                    activar.background = thiscontext.getDrawable(R.drawable.btn_btn_final)
                    activar_opcion=false
                }



            }, Response.ErrorListener { error ->
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_ESTADO] = estado
                params[KEY_CODIGO] = idArray[posicion_r]


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }





    fun obtenerEmail(): String? {
        var preferenfs= activity?.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }

    fun guardarInvernaderoClick(posicion:Int){
        var preferenfs= activity?.getSharedPreferences("Invernadero", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs!!.edit()
        editor.putInt("invernaderoClick",posicion)
        editor.apply()
    }



    companion object {
        fun newInstance(param1: String, param2: String) =
            List_Invernaderos().apply {
            }
    }
}