package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registrar.*
import kotlinx.android.synthetic.main.activity_registrar.btn_registrar
import kotlinx.android.synthetic.main.activity_registrar.email_p
import kotlinx.android.synthetic.main.activity_registrar.password_p
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class RegistrarActivity : AppCompatActivity() {
    private lateinit var txtnombre: EditText
    private lateinit var txtnombre_p: TextInputLayout
    private lateinit var txtapellido: EditText
    private lateinit var txtapellido_p: TextInputLayout
    private lateinit var txtemail: EditText
    private lateinit var txtemail_p: TextInputLayout
    private lateinit var txtpassword: EditText
    private lateinit var txtpassword_p: TextInputLayout
    private lateinit var textPhone: EditText
    private lateinit var textPhone_p : TextInputLayout
    lateinit var animacion: LottieAnimationView
    lateinit var btn_atras : LinearLayout

   var correoOficial = ""
   var contraseñaOficial = ""
    lateinit var session : Session


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registrar)

        correoOficial  = "cannabisautomatico@gmail.com"
        contraseñaOficial  = "CannabisAutomaticoFup2020"


        //Buscamos las vistas (elementos creados en la vista)
            txtnombre = findViewById(R.id.nombre_e)
            txtnombre_p = findViewById(R.id.nombre_p)
            txtapellido = findViewById(R.id.apellido_e)
            txtapellido_p = findViewById(R.id.apellido_p)
            txtemail = findViewById(R.id.email_e)
            txtemail_p = findViewById(R.id.email_p)
            txtpassword = findViewById(R.id.password_e)
            txtpassword_p = findViewById(R.id.password_p)
            textPhone = findViewById(R.id.telefono)
            textPhone_p = findViewById(R.id.telefono_p)
            btn_atras = findViewById(R.id.btn_atras)
            animacion = findViewById(R.id.cargando)

        btn_atras.setOnClickListener{
          //  val intent:Intent = Intent(this, MainActivity::class.java)
           // startActivity(intent)
            finish()
        }



            btn_registrar.setOnClickListener{
                validate()
            }

        }

    fun validate() {
        var emailValidado : Boolean = false
        var passValidado : Boolean = false
        var nomValidado : Boolean = false
        var apeValidado : Boolean = false
        var telValidate : Boolean = false

        var mailError: String? = null
        if (TextUtils.isEmpty(txtnombre.text)) {
            mailError = getString(R.string.mandatory)
        }else{
            nomValidado = true
        }
        toggleTextInputLayoutError(txtnombre_p, mailError)

        if (TextUtils.isEmpty(txtapellido.text)) {
            mailError = getString(R.string.mandatory)
        }else{
            apeValidado = true
        }
        toggleTextInputLayoutError(txtapellido_p, mailError)

        if (TextUtils.isEmpty(textPhone.text)) {
            mailError = getString(R.string.mandatory)
        }else{
            telValidate = true
        }
        toggleTextInputLayoutError(textPhone_p, mailError)

        if (TextUtils.isEmpty(txtemail.text)) {
            mailError = getString(R.string.mandatory)
        }else{
            if (!validarEmail(txtemail.text.toString().trim())){
                mailError = getString(R.string.mandatoryemail)
            }else{
                emailValidado = true
            }

        }
        toggleTextInputLayoutError(txtemail_p, mailError)
        var passError: String? = null
        if (TextUtils.isEmpty(txtpassword.text)) {
            passError = getString(R.string.mandatory)
        }else{
            passValidado = true
        }
        toggleTextInputLayoutError(txtpassword_p, passError)
        //clearFocus()

        if (emailValidado && passValidado && nomValidado && apeValidado ){
            registrarUsuario()
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

    fun registrarUsuario(){

        var nombre :String = txtnombre.text.toString().trim()
        var apellido :String = txtapellido.text.toString().trim()
        var usuario :String = txtemail.text.toString().trim()
        var password :String = txtpassword.text.toString().trim()
        var tel : String = textPhone.text.toString().trim()

        var response = Response.Listener<String> { response ->
            try {
                val json = JSONObject(response)
                var success = json.getBoolean("success")

                if (success==true) {
                    animacion.visibility = View.VISIBLE
                    val handler = Handler()
                    handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                        val intent = Intent(this, CodValidarActivity::class.java)
                        intent.putExtra("usuario", usuario)
                        intent.putExtra("nombre", nombre)
                        startActivity(intent)
                        Toast.makeText(
                            this,
                            "Usuario Registrado con exito",
                            Toast.LENGTH_LONG
                        ).show()
                    }, 1000)
                    metodoCorreo()
                    guardando_UsuarioPassword()

                } else {

                    Toast.makeText(
                        this,
                        "Error el usuario ya existe",
                        Toast.LENGTH_LONG
                    ).show()
                    var mailError: String? = null
                        mailError = getString(R.string.mandatoryUsu)
                        toggleTextInputLayoutError(txtemail_p, mailError)

                    animacion.visibility = View.VISIBLE
                    val handler = Handler()
                    handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                        animacion.visibility = View.GONE
                    }, 1000)

                }
            } catch (e: JSONException) {
            }

        }

        var registro = RegisterRequest(nombre,apellido,usuario,password,tel,response)
        var queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(registro)
    }

    fun metodoCorreo(){
        var Contenido: String ="<h1>Te damos la bienvenida a CannabisAutomaticoApp,</h1>"+
                "        <h2>tu nueva cuenta ha sido creada.</h2>" + "<br>" +
                "        <p>Aquí encontrarás algunas sugerencias para comenzar," +
                "        tu codigo para terminar de activar</p>" +
                "        <p>tu codigo es:<b> CA-001VP </b>" + "<br>" + "<br>" +
                "        <p>Por favor no responder a este correo ya que solo</p>" +
                "        <p> es de enviar mensaje y no recibir.</p>" + "<br>" +
                "        <h3><p>Cannabis Automatico App</p></h3>"

        val politica : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(politica)

        val properties = Properties()
            properties["mail.smtp.host"] = "smtp.googlemail.com"
            properties["mail.smtp.socketFactory.port"] = "465"
            properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.port"] = "465"


        try {
           session = Session.getDefaultInstance(properties,object:Authenticator(){
               override fun getPasswordAuthentication(): PasswordAuthentication {
                   return PasswordAuthentication(correoOficial,contraseñaOficial)
               }
           })

            val mensaje: Message = MimeMessage(session)
            mensaje.setFrom(InternetAddress(correoOficial))
            mensaje.subject = "Hola, ${txtnombre.text}"
            mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(txtemail.text.toString())
            )
            mensaje.setContent(Contenido,"text/html; charset=utf-8")
            Transport.send(mensaje)


        }catch (e:Exception){e.printStackTrace()}



    }

    fun guardando_UsuarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putString("email", txtemail.text.toString())
        editor.putString("password", txtpassword.text.toString())
        editor.apply()
    }

    }
