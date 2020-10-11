package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.*
import androidx.annotation.RequiresApi
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_registrar.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Perfil : AppCompatActivity() {

    lateinit var imagen : CircleImageView
    var imagenSiguiente : Int = 0
    var usuarioId: String = ""
    lateinit var nombre_P : TextView
    lateinit var sesion : Button
    lateinit var btn_atras :LinearLayout
    lateinit var invernidad_cantida :TextView
    lateinit var usuario_perfil :TextView
    lateinit var tel_perfil :TextView
    lateinit var panel :TextView
    var miLista = ArrayList<String>()

    var URL_USUARIO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/ConsultaUsuario.php"
    var KEY_EMAIL = "email"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        imagen = findViewById(R.id.imageView)
        nombre_P = findViewById(R.id.nombre_p)
        sesion = findViewById(R.id.Sesion)
        btn_atras = findViewById(R.id.btn_atras)
        usuario_perfil = findViewById(R.id.cuenta_perfil)
        tel_perfil = findViewById(R.id.tel_perfil)
        invernidad_cantida = findViewById(R.id.invernadero_cantidad)
        panel = findViewById(R.id.panel_info)

        panel.isSelected = true

        obtnerUsuario()


        btn_atras.setOnClickListener {
            finish()
        }

        imagenSiguiente = obtenerEstadoImagen()
        if (imagenSiguiente==1){
            imagen.setBackgroundResource(R.drawable.burro)
        }else if (imagenSiguiente == 2){
            imagen.setBackgroundResource(R.drawable.panda)
        }else{
            imagen.setBackgroundResource(R.drawable.logo_actual)
        }

        imagen.setOnClickListener {
            imagenSiguiente++
            estadoImagen(imagenSiguiente)
            if (imagenSiguiente==1){
                imagen.setBackgroundResource(R.drawable.burro)

            }else if (imagenSiguiente == 2){
                imagen.setBackgroundResource(R.drawable.panda)
            }else{
                imagen.setBackgroundResource(R.drawable.logo_actual)
                imagenSiguiente=0
            }

        }

        sesion.setOnClickListener{
            estadoNoCerrarSesion(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }


    fun obtnerUsuario() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_USUARIO,
            Response.Listener { response ->
                val json = JSONObject(response)
                try {
                    val jsonarray = json.getJSONArray("informacion")
                    var cantidad = json.getString("cantidad")
                    var nombre = json.getString("nombre_a")
                    var apellido = json.getString("apellido")
                    var email = json.getString("email")
                    var tel = json.getString("tel")
                    nombre_P.text = "$nombre $apellido"
                    usuario_perfil.text=email
                    invernidad_cantida.text=cantidad.toString()
                    tel_perfil.text = tel


                    var jsonA : JSONObject
                    var nameinver : String
                    var etapa: String
                    for (i in 0 until jsonarray.length()) {
                        jsonA = jsonarray.getJSONObject(i)
                        nameinver = jsonA.getString("nombre")
                        etapa = jsonA.getString("etapa")
                        miLista.add("$nameinver: $etapa")


                    }
                    panel.text=miLista.toString().replace("[", "").replace("]", "");


                }catch (e:Exception){
                    Toast.makeText(this,"$e aqui",Toast.LENGTH_LONG).show()

                }

            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG).show()
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
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)


    }


    fun estadoNoCerrarSesion(b:Boolean){
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putBoolean("estado.sesion",b)
        editor.apply()
    }

    fun obtenerNombreApellido(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var nombre = preferenfs.getString("nombre","")
        var apellido =  preferenfs.getString("apellido","")
        return "$nombre $apellido"
    }


    fun obtenerEmail(): String? {
        var preferenfs= this.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }
    fun estadoImagen(imagen : Int){
        var preferenfs= getSharedPreferences(usuarioId, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putInt("imagen", imagen)
        editor.apply()
    }
    fun obtenerEstadoImagen(): Int {
        var preferenfs= getSharedPreferences(usuarioId, Context.MODE_PRIVATE)
        return  preferenfs.getInt("imagen", 0)
    }
}
