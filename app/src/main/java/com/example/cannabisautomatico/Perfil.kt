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

    var URL_USUARIO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/ConsultaUsuario.php"
    var URL_INVERNADERO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/consultaInvernadero.php"
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
        invernidad_cantida = findViewById(R.id.invernadero_cantidad)

        obtnerNombre()
        obtnerInvernadero()
        usuario_perfil.text=obtenerEmail()

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

    fun obtnerNombre() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_USUARIO,
            Response.Listener { response ->
                 val json = JSONObject(response)
                var success = json.getBoolean("success")
                if (success) {
                    var nombre = json.getString("nombre")
                    var apellido = json.getString("apellido")
                    nombre_P.text = "$nombre $apellido"
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
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
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun obtnerInvernadero() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_INVERNADERO,
            Response.Listener { response ->
                    invernidad_cantida.text = "$response"

            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
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
