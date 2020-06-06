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
            /*
            if (expandablePosition != -1 && position != expandablePosition){
                expandablelistview?.collapseGroup(expandablePosition)
            }
            expandablePosition = position

             */
        }



        expandablelistview?.onItemLongClickListener = AdapterView.OnItemLongClickListener { av, v, pos, id ->
            // TransitionManager.beginDelayedTransition(contenedorP)
            seleccionado = !seleccionado
            if (seleccionado) {
                animacion("alpha")
                menu.visibility = (View.VISIBLE)
                btn_mas_invernadero.visibility = (View.INVISIBLE)
                 Adapter_invernadero(thiscontext,seleccionado)

            }else{
                TransitionManager.beginDelayedTransition(contenedorP)
                menu.visibility = (View.INVISIBLE)
                btn_mas_invernadero.visibility = (View.VISIBLE)
                Adapter_invernadero(thiscontext,seleccionado)

            }


            true
        }




        expandablelistview?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val fr1= Home_fr()
            val trantition1 : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            trantition1.replace(R.id.contenedor, fr1)
            trantition1.commit()
            guardarInvernaderoClick(groupPosition)
            activity?.Descripcion?.text= resources.getText(R.string.Info_sensores)
            false
        }


            this.expanablelistAdapter = Adapter_invernadero(thiscontext, expandableTITULO, listaDETALLE)
            expandablelistview?.setAdapter(this.expanablelistAdapter)


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
                    }

                } catch (e: JSONException) {
                    Toast.makeText(thiscontext, "" + e, Toast.LENGTH_LONG).show()
                }

            }, Response.ErrorListener { error ->

                Toast.makeText(activity, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var email:String = "hurtadoandres942@gmail.com"

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = email

                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(activity)
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