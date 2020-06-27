package com.example.cannabisautomatico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class Olvide_passwordActivity : AppCompatActivity() {

    lateinit var btn_atras : LinearLayout
    lateinit var txtemail : EditText
    lateinit var btn_recuperar :Button
    var random = Random()

    lateinit var cargando : LottieAnimationView

    var URL_USUARIO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/ConsultaUsuario.php"
    var KEY_EMAIL = "email"

    var email : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvide_password)

        btn_atras = findViewById(R.id.btn_atras)
        txtemail = findViewById(R.id.email)
        btn_recuperar = findViewById(R.id.btn_recuperar)
        cargando = findViewById(R.id.cargando)



        btn_atras.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_recuperar.setOnClickListener {
            email = txtemail.text.toString()
            cambioClave(email)

        }

    }

    fun cambioClave(email:String) {
        cargando.visibility = (View.VISIBLE)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_USUARIO,
            Response.Listener { response ->
                cargando.visibility = (View.INVISIBLE)
                val json = JSONObject(response)
                var success = json.getBoolean("success")
                if (success) {
                    var codigo_random = (1000 + random.nextInt((9999) - 1000))
                    var intent = Intent(this, verificacion_codigo_cambio_clave::class.java)
                    intent.putExtra("codigo",codigo_random)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No pudimos encontrar tu cuenta de cannabis", Toast.LENGTH_LONG)
                        .show()
                }
            }, Response.ErrorListener { error ->
                cargando.visibility = (View.INVISIBLE)
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = email


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }
}
