package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_cod_validar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class CodValidarActivity : AppCompatActivity() {
   lateinit var codigo : EditText
    lateinit var codigo_p : TextInputLayout
    lateinit var email : EditText
    private lateinit var progressBar: ProgressBar
    var usuarioID:String?=null
    var nombreBD:String?=null

    lateinit var btn_atras : LinearLayout

    private lateinit var  handler : Handler
    lateinit var animacion:LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cod_validar)
        codigo = findViewById(R.id.codigo)
        codigo_p = findViewById(R.id.codigo_p)
        email = findViewById(R.id.email)
        animacion = findViewById(R.id.activado)
        btn_atras = findViewById(R.id.btn_atras)

        var campo_email = findViewById<TextView>(R.id.email)
        campo_email.isEnabled=false

        btn_atras.setOnClickListener{
           // val intent:Intent = Intent(this, BienvenidoActivity::class.java)
            //startActivity(intent)
            finish()
        }


        val bundle = intent.extras
        if (bundle != null) {
            usuarioID = bundle.getString("usuario")
        }
        email.setText(obtenerUsuario())


        btn_validar.setOnClickListener {
           validate()
        }

    }

    fun validate() {
        var codigoValidado : Boolean = false


        var mailError: String? = null
        if (TextUtils.isEmpty(codigo.text)) {
            mailError = getString(R.string.mandatory)
        }else{
                codigoValidado = true
        }
        toggleTextInputLayoutError(codigo_p, mailError)
        if (codigoValidado){
            validarCodigo()
        }
    }

    private fun toggleTextInputLayoutError(
        textInputLayout: TextInputLayout,
        msg: String?
    ) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = msg != null
    }

    fun validarCodigo(){
        var codigo = codigo.text.toString()
        var response =  Response.Listener<String> { response ->
            try {
                val json = JSONObject(response)
                var success = json.getBoolean("success")
                if (success){
                    ConsultaRequest(email.text.toString(),codigo)

                }else{
                    var mailError: String? = null
                    mailError = getString(R.string.mandatoryCodigo)
                    toggleTextInputLayoutError(codigo_p, mailError)
                }
            }catch (e: JSONException){}

        }
        var registro = RegisterValidaRequest(email.text.toString(),codigo,response)
        var queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(registro)
    }

    fun ConsultaRequest(email:String, codigo:String){
        var response =  Response.Listener<String> { response ->
            try {
                val json = JSONObject(response)
                var success = json.getBoolean("success")
                Toast.makeText(this, "$success" ,Toast.LENGTH_LONG)
                if (success){
                    var validado = json.getString("estado")
                    if (validado=="true") {
                        Toast.makeText(this, "Registro con exito", Toast.LENGTH_LONG)

                        animacion.visibility=(View.VISIBLE)
                        handler = Handler()
                        handler.postDelayed({

                            startActivity(Intent(this, HomeActivity::class.java))
                        }, 1000)

                    }
                }else{
                    var mailError: String? = null
                    mailError = getString(R.string.mandatoryCodigo)
                    toggleTextInputLayoutError(codigo_p, mailError)
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
