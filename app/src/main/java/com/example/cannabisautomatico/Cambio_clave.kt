package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_cambio_clave.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.email_p
import kotlinx.android.synthetic.main.activity_main.password
import kotlinx.android.synthetic.main.activity_main.password_p
import java.util.*
import java.util.regex.Pattern

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
            validate()//valida los campos que no se encuentren vacion y si todo esta bien ejecuta el metodo actualizar  esta dentro del metodo validate
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

    fun validate() {
        var passValidado : Boolean = false
        var passValidado2 : Boolean = false

        var mailError: String? = null
        if (TextUtils.isEmpty(password_primero.text)) {
            mailError = getString(R.string.mandatory)
        }else{

                passValidado = true


        }
        toggleTextInputLayoutError(password_p2, mailError)
        var passError: String? = null
        if (TextUtils.isEmpty(password_segundo.text)) {
            passError = getString(R.string.mandatory)
        }else{
            pass1 = password_primero.text.toString()
            pass2 = password_segundo.text.toString()
            if (pass1 == pass2){
                passValidado2 = true
            }else{
                passError = getString(R.string.mandatoryPass)
            }

        }
        toggleTextInputLayoutError(password_p, passError)
        //clearFocus()

        if (passValidado && passValidado2){
            actualizarPassword()
        }
    }

    private fun toggleTextInputLayoutError(
        textInputLayout: TextInputLayout,
        msg: String?
    ) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = msg != null
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