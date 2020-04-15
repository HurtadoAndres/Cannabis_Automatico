package com.example.cannabisautomatico

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.app.VoiceInteractor
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.service.voice.VoiceInteractionSession
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class MainActivity: AppCompatActivity() {

    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText
    private lateinit var auth: FirebaseAuth

    var nombredBD: String=""
    var usuarioID: String=""

    var context : Context = this

    lateinit var animacion: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtemail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.password)
        auth = FirebaseAuth.getInstance()

        animacion=findViewById(R.id.cargando)

       // var usuario=email.text.toString()
       // var password = password.text.toString()
        cargar_UusarioPassword()

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
            var usuario=txtemail.text.toString()
            var password = txtpassword.text.toString()
            if (usuario.isNotEmpty() && password.isNotEmpty()) {
                var response = Response.Listener<String> { response ->
                    try {
                        val json = JSONObject(response)
                        var success = json.getBoolean("success")
                        if (success) {

                            var estadodBD = json.get("estado")
                            usuarioID = json.getString("Email")
                            nombredBD = json.getString("Nombre")
                            if (estadodBD=="true") {


                                animacion.visibility = View.VISIBLE
                                val handler = Handler()
                                handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                                    var intent=Intent(this, HomeActivity::class.java)
                                    var usu = usuarioID
                                    intent.putExtra("email", usu)
                                    startActivity(intent)
                                    finish()
                                }, 1000)

                            }else{
                                alerta()
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Error Usuario y/o contraseña Incorrecto",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: JSONException) {
                    }

                }
                var loginrequest = LoginRequest(usuario, password, response)
                var queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(loginrequest)

            }else{
                Toast.makeText(this, "Los campos estan vacios", Toast.LENGTH_LONG).show()
            }

            guardando_UsuarioPassword()
        }


    }

    fun guardando_UsuarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putString("email", txtemail.text.toString())
        editor.putString("password", txtpassword.text.toString())
        editor.commit()
    }
    fun cargar_UusarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var usuario : String? = preferenfs.getString("email", "")
        var password : String? = preferenfs.getString("password", "")

        txtemail.setText(usuario)
        txtpassword.setText(password)
    }


    fun alerta(): AlertDialog? {
        val builder = AlertDialog.Builder(this)
            .setMessage("Bienvenido $nombredBD! \n" +
                    "Debes de activar tu cuenta Antes de  \n" +
                    "iniciar")
            .setCancelable(false)

            .setPositiveButton("OK") { dialog, which ->
             var intent =  Intent(this, CodValidarActivity::class.java)
                intent.putExtra("nombre",nombredBD.toString())
                startActivity(intent)
                finish()

            }
            .setNegativeButton("CANCELAR"){dialog, which -> dialog.cancel() }

        builder.setTitle("Activar cuenta").show()
        return builder.create()
    }

    /* METODO PARA INGRESAR CON FIREBASE
    private fun loginUser(){
        val user:String = txtemail.text.toString()
        val password:String = txtpassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
                progressBar.visibility= View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task ->

                    if (task.isSuccessful){
                        IrAlHome()
                    }else{
                        Toast.makeText(this,"Error en la Autenticacion", Toast.LENGTH_LONG).show()
                    }
        }
        }
    }
  */

    /* utilizado con el metodo firebase
    private fun IrAlHome(){
        startActivity(Intent(this,HomeActivity::class.java))
    }
    */



}
