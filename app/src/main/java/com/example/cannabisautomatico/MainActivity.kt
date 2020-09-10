package com.example.cannabisautomatico

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern


class MainActivity: AppCompatActivity() {

    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText

    lateinit var btn_atras :LinearLayout
    lateinit var btn_iniciar :Button
    lateinit var btn_recuperar : Button
    lateinit var btn_registrar : Button


    var nombredBD: String=""
    var usuarioID: String=""
    var apellidodBD: String=""

    var context : Context = this

    lateinit var animacion: LottieAnimationView

    lateinit var RBNoCerarSesion : RadioButton
    var estActivadoRBSession : Boolean = false
    var USU = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_atras = findViewById(R.id.btn_atras)
        btn_iniciar = findViewById(R.id.btn_iniciar)
        btn_recuperar = findViewById(R.id.btn_recuperar)
        btn_registrar = findViewById(R.id.btn_registrar)

        txtemail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.password)

        USU = findViewById<EditText>(R.id.email).text.toString()

        animacion=findViewById(R.id.cargando)
        RBNoCerarSesion = findViewById(R.id.NoCerrarSesion)

        if (obtenerEstado()){
            obtenerEmail()
        }
        estActivadoRBSession = RBNoCerarSesion.isChecked
        RBNoCerarSesion.setOnClickListener{
            if (estActivadoRBSession){
                RBNoCerarSesion.isChecked=false
            }
            estActivadoRBSession = RBNoCerarSesion.isChecked
        }


        btn_registrar.setOnClickListener{
            val intent:Intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }



        btn_atras.setOnClickListener{
            val intent:Intent = Intent(this, BienvenidoActivity::class.java)
            startActivity(intent)
        }

        btn_recuperar.setOnClickListener{
            val intent:Intent = Intent(this, Olvide_passwordActivity::class.java)
            startActivity(intent)
        }





        btn_iniciar.setOnClickListener{
            validate()//valida y si es todo correcto inicia sesion metodo dentro de validate
        }




    }

    fun login(){
        var usuario=txtemail.text.toString()
        var password = txtpassword.text.toString()
            var response = Response.Listener<String> { response ->
                try {
                    val json = JSONObject(response)
                    var success = json.getBoolean("success")
                    if (success) {

                        var estadodBD = json.get("estado")
                        usuarioID = json.getString("Email")
                        nombredBD = json.getString("Nombre")
                        apellidodBD = json.getString("Apellido")
                        if (estadodBD=="true") {
                            obtenerEstadoNoCerrarSesion() //preferens
                            animacion.visibility = View.VISIBLE
                            irhome()
                            guardando_UsuarioPassword()


                        }else{
                            alerta()
                        }

                    } else {
                        var existeUsu = json.getInt("existe")

                        if (existeUsu > 0) {
                            Toast.makeText(
                                this,
                                "Error Usuario y/o contraseña Incorrecto",
                                Toast.LENGTH_LONG
                            ).show()
                        }else{
                            Toast.makeText(
                                this,
                                "Error, el usuario no se encuentra registrado",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: JSONException) {
                }

            }
            var loginrequest = LoginRequest(usuario, password, response)
            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(loginrequest)
            estadoNoCerrarSesion()

    }

    fun validate() {
        var emailValidado : Boolean = false
        var passValidado : Boolean = false

        var mailError: String? = null
        if (TextUtils.isEmpty(email.text)) {
            mailError = getString(R.string.mandatory)
        }else{
            if (!validarEmail(email.text.toString().trim())){
                mailError = getString(R.string.mandatoryemail)
            }else{
                emailValidado = true
            }

        }
        toggleTextInputLayoutError(email_p, mailError)
        var passError: String? = null
        if (TextUtils.isEmpty(password.text)) {
            passError = getString(R.string.mandatory)
        }else{
            passValidado = true
        }
        toggleTextInputLayoutError(password_p, passError)
        //clearFocus()

        if (emailValidado && passValidado){
            login()
        }
    }

    private fun toggleTextInputLayoutError(
        textInputLayout: TextInputLayout,
        msg: String?
    ) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = msg != null
    }

    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun irhome(){
        val handler = Handler()
        handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
            var intent = Intent(this, HomeActivity::class.java)
            var usu = usuarioID
            intent.putExtra("email", usu)
            startActivity(intent)
            finish()
        }, 1000)
    }

    fun estadoNoCerrarSesion(){
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putBoolean("estado.sesion", RBNoCerarSesion.isChecked)
        editor.apply()
    }
    fun obtenerEstadoNoCerrarSesion():Boolean{
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        return  preferenfs.getBoolean("estado.sesion", false)
    }



    fun guardando_UsuarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
            editor.putString("email", txtemail.text.toString())
            editor.putString("password", txtpassword.text.toString())
            editor.putString("nombre", nombredBD)
            editor.putString("apellido", apellidodBD)
        editor.apply()
    }

    fun obtenerEstado(): Boolean {
        var preferenfs= this.getSharedPreferences("estado_usu", Context.MODE_PRIVATE)
        return  preferenfs.getBoolean("estado", false)
    }

    fun obtenerEmail() {
        var preferenfs= this.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
         txtemail.setText(preferenfs?.getString("email", ""))
        txtpassword.setText(preferenfs?.getString("password", ""))
    }


    fun alerta(): AlertDialog? {
        val builder = AlertDialog.Builder(this)
            .setMessage(
                "Bienvenido $nombredBD! \n" +
                        "Debes de activar tu cuenta Antes de  \n" +
                        "iniciar"
            )
            .setCancelable(false)

            .setPositiveButton("OK") { dialog, which ->
             var intent =  Intent(this, CodValidarActivity::class.java)
                intent.putExtra("nombre", nombredBD.toString())
                startActivity(intent)
                finish()

            }
            .setNegativeButton("CANCELAR"){ dialog, which -> dialog.cancel() }

        builder.setTitle("Activar cuenta").show()
        return builder.create()
    }



}
