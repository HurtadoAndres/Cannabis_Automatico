package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cod_validar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class CodValidarActivity : AppCompatActivity() {
   lateinit var codigo : EditText
    lateinit var email : EditText
    private lateinit var progressBar: ProgressBar
    var usuarioID:String?=null
    var nombreBD:String?=null

    private lateinit var  handler : Handler
    lateinit var animacion:LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cod_validar)
        codigo = findViewById(R.id.codigo)
        email = findViewById(R.id.email)
        animacion = findViewById(R.id.activado)

        var campo_email = findViewById<TextView>(R.id.email)
        campo_email.isEnabled=false

        btn_atrasss.setOnClickListener{
            val intent:Intent = Intent(this, BienvenidoActivity::class.java)
            startActivity(intent)
        }


        val bundle = intent.extras
        if (bundle != null) {
            usuarioID = bundle.getString("usuario")
        }
        email.setText(obtenerUsuario())


        btn_validar.setOnClickListener {
            var codigo = codigo.text.toString()
            var response =  Response.Listener<String> { response ->
                try {
                    val json = JSONObject(response)
                    var success = json.getBoolean("success")
                    if (success){
                        ConsultaRequest(email.text.toString(),codigo)

                    }else{
                        Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                    }
                }catch (e: JSONException){}

            }
            var registro = RegisterValidaRequest(email.text.toString(),codigo,response)
            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(registro)
        }

    }

    fun ConsultaRequest(email:String, codigo:String){
        var response =  Response.Listener<String> { response ->
            try {
                val json = JSONObject(response)
                var success = json.getBoolean("success")
                if (success){
                    var validado = json.get("estado")
                    if (validado=="true") {
                        Toast.makeText(this, "Registro con exito", Toast.LENGTH_LONG)

                        animacion.visibility=(View.VISIBLE)
                        handler = Handler()
                        handler.postDelayed({

                            startActivity(Intent(this, HomeActivity::class.java))
                        }, 1000)

                    }
                }else{
                    Toast.makeText(this,"Error verifica el codigo", Toast.LENGTH_LONG).show()
                }
            }catch (e: JSONException){}

        }
        var registro = ConsultaCodvalidacion(email,codigo,response)
        var queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(registro)
    }


    fun obtenerUsuario(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs.getString("email","")
    }
}
