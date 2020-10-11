package com.example.cannabisautomatico




import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class calendario : Fragment()  {

    lateinit var listview : ListView
    lateinit var adaptador : Adapter
    var invernadero_name : String = ""
    var etapa : String = ""
    var dias_cantidad : Int = 0
    var URL_CONSULTA = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/calendarioEtapa.php"
    var KEY_EMAIL = "email"
    lateinit var lisitem : ArrayList<entidad_calendario>
    lateinit var sindatos : ImageView
    lateinit var fondo_cargando: LottieAnimationView


    lateinit var thiscontext : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendario, container, false)
        lisitem = ArrayList()
        sindatos = view.findViewById(R.id.NOdatos)
        fondo_cargando = view.findViewById(R.id.fondo_cargando)
        if (container != null) {
            thiscontext = container.context
        }


        listview = view.findViewById(R.id.listview)
        calendarioInvernadero()



        return view
    }


    fun array(inver: String, et: String, d_cantidad: Int):ArrayList<entidad_calendario>{
        lisitem.add(entidad_calendario(inver, et, d_cantidad))
          return lisitem
    }

    fun calendarioInvernadero() {
        fondo_cargando.visibility=(View.VISIBLE)
        val stringRequest: StringRequest = @RequiresApi(Build.VERSION_CODES.O)
        object : StringRequest(
            Method.POST, URL_CONSULTA,
            Response.Listener { response ->
                fondo_cargando.visibility = (View.INVISIBLE)
                val json = JSONObject(response)
                if (json.length() == 1) {
                    sindatos.visibility = (View.VISIBLE)
                }
                try {
                    val jsonarray = json.getJSONArray("calendario")

                    for (i in 0 until jsonarray.length()) {

                        var jsonA = jsonarray.getJSONObject(i)
                        invernadero_name = jsonA.getString("invernadero")

                        var fecha_i : String = jsonA.getString("fecha_i")
                        var fecha_f : String  = jsonA.getString("fecha_f")


                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val current = LocalDateTime.now()

                        var fechaInicial: Date = dateFormat.parse(fecha_i)
                        var fechaFinal: Date = dateFormat.parse(current.toString())

                        var dias = ((fechaFinal.time - fechaInicial.time) / 86400000).toInt()




                        if(dias <= 7){
                          dias_cantidad=dias
                            etapa="Etapa 1"
                        }

                       else if(dias in 8..22){
                            dias_cantidad=dias
                            etapa="Etapa 2"
                        }

                       else if(dias in 23..112){

                            dias_cantidad=dias
                            etapa="Etapa 3"
                        }

                        else if (dias in 113..202){

                            dias_cantidad=dias
                            etapa="Etapa 4"
                        }



                        adaptador = adaptador_calendario(
                            thiscontext, array(
                                invernadero_name,
                                etapa,
                                dias_cantidad
                            )
                        )
                    }


                    listview.adapter = adaptador as adaptador_calendario

                } catch (e: Exception) {
                }


            }, Response.ErrorListener { error ->
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = obtenerEmail().toString()


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }

    fun dias(f_i: String , f_f: String): Int{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val fechaInicial: Date? = dateFormat.parse(f_i)
        val fechaFinal: Date? = dateFormat.parse(f_f)

        val dias = ((fechaFinal!!.time - fechaInicial!!.time) / 86400000).toInt()
        // Toast.makeText(thiscontext,"$dias",Toast.LENGTH_LONG).show()

        return dias;
    }

    fun obtenerEmail(): String? {
        var preferenfs= activity?.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email", "")
    }



    companion object {

        fun newInstance(param1: String, param2: String) =
            calendario().apply {

            }
    }
}
