package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registrar.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class RegistrarActivity : AppCompatActivity() {
    private lateinit var txtnombre: EditText
    private lateinit var txtapellido: EditText
    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText
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
            txtapellido = findViewById(R.id.apellido_e)
            txtemail = findViewById(R.id.email_e)
            txtpassword = findViewById(R.id.password_e)
            btn_atras = findViewById(R.id.btn_atras)
            animacion = findViewById(R.id.cargando)

        btn_atras.setOnClickListener{
          //  val intent:Intent = Intent(this, MainActivity::class.java)
           // startActivity(intent)
            finish()
        }



            btn_registrar.setOnClickListener{

                var nombre :String = txtnombre.text.toString().trim()
                var apellido :String = txtapellido.text.toString().trim()
                var usuario :String = txtemail.text.toString().trim()
                var password :String = txtpassword.text.toString().trim()

                if(!nombre.isEmpty() && !apellido.isEmpty() && !usuario.isEmpty() && !password.isEmpty()) {
                    var response = Response.Listener<String> { response ->
                        try {
                            val json = JSONObject(response)
                            var success = json.getBoolean("success")
                            if (success) {
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
                                animacion.visibility = View.VISIBLE
                                val handler = Handler()
                                handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                                    animacion.visibility = View.GONE
                                }, 1000)

                            }
                        } catch (e: JSONException) {
                        }

                    }

                var registro = RegisterRequest(nombre,apellido,usuario,password,response)
                var queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(registro)
                }else{
                    Toast.makeText(this, "Los campos estan vacios", Toast.LENGTH_LONG).show()
                }

            }

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
