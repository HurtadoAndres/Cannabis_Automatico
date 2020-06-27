package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*

class Cambio_clave : AppCompatActivity() {

    lateinit var  btn_confirmar: Button
    lateinit var  btn_atras: LinearLayout
    lateinit var password_primero : EditText
    lateinit var password_segundo : EditText

    lateinit var cargando : LottieAnimationView

    lateinit var pass1 : String
    lateinit var pass2 : String

    var email_C : String = ""

    var KEY_EMAIL = "email"
    var KEY_PASSWORD = "password"
    var URL_ACTUALIZAR = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/cambio_de_clave.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambio_clave)
        btn_confirmar = findViewById(R.id.btn_confirmar)
        password_primero = findViewById(R.id.password_p2_s)
        password_segundo = findViewById(R.id.password)
        cargando = findViewById(R.id.cargando)
        btn_atras = findViewById(R.id.btn_atras)

        var pagina = intent?.getIntExtra("pagina", 0)
        email_C = obtenerEmail().toString()

        btn_confirmar.setOnClickListener {
            pass1 = password_primero.text.toString()
            pass2 = password_segundo.text.toString()
            if (pass1 == pass2){
                actualizarPassword()
            }else{
                Toast.makeText(this,"Las contraseñas son diferentes", Toast.LENGTH_LONG).show()
            }
        }

        btn_atras.setOnClickListener {
            if (pagina == 1) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("pagina_fr", 4)
                startActivity(intent)
            }else{
                startActivity(Intent(this, Olvide_passwordActivity::class.java))
            }
        }

    }

    fun actualizarPassword() {
        cargando.visibility = (View.VISIBLE)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_ACTUALIZAR,
            Response.Listener { response ->
                cargando.visibility = (View.INVISIBLE)
                startActivity(Intent(this, MainActivity::class.java))
            }, Response.ErrorListener { error ->
                cargando.visibility = (View.INVISIBLE)
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = email_C.toString()
                params[KEY_PASSWORD]=  password_primero.text.toString()


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun obtenerEmail(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }


}