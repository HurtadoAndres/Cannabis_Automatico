package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrar.*
import org.json.JSONException
import org.json.JSONObject


class RegistrarActivity : AppCompatActivity() {
    private lateinit var txtnombre: EditText
    private lateinit var txtapellido: EditText
    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText
    private lateinit var txtcodigo: EditText
    private lateinit var dbReferences: DatabaseReference
    private lateinit var dataBase: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    var ccodigoUSU=Any()
    var nombreBD= Any()
    var usuarioID= Any()



    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registrar)

            //Buscamos las vistas (elementos creados en la vista)
            txtnombre = findViewById(R.id.nombre)
            txtapellido = findViewById(R.id.apellido)
            txtemail = findViewById(R.id.email)
            txtpassword = findViewById(R.id.password)


            progressBar = findViewById(R.id.progressBar)

            dataBase = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()

            dbReferences = dataBase.reference.child("User")

            btn_registrar.setOnClickListener{
               // Create_nueva_cuenta()
                var nombre :String = txtnombre.text.toString()
                var apellido :String = txtapellido.text.toString()
                var usuario :String = txtemail.text.toString()
                var password :String = txtpassword.text.toString()
                var response =  Response.Listener<String> { response ->
                    try {
                        val json = JSONObject(response)
                        var success = json.getBoolean("success")
                        if (success){
                            progressBar.visibility = View.VISIBLE
                            val handler = Handler()
                            handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                                progressBar.visibility = View.GONE
                            }, 4500)
                            Toast.makeText(this,"Usuario Registrado con exito", Toast.LENGTH_LONG).show()
                           val intent= Intent(this, CodValidarActivity::class.java)
                            intent.putExtra("usuario",usuario )
                            intent.putExtra("nombre", nombre )
                            startActivity(intent)
                        }else{

                            Toast.makeText(this,"Error el usuario ya existe", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.VISIBLE
                            val handler = Handler()
                            handler.postDelayed(Runnable { // acciones que se ejecutan tras los milisegundos
                                progressBar.visibility = View.GONE
                            }, 4500)

                        }
                    }catch (e: JSONException){}

                }
                var registro = RegisterRequest(nombre,apellido,usuario,password,response)
                var queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(registro)


            }

        }


/*

        private fun Create_nueva_cuenta(){
            val name:String = txtnombre.text.toString()
            val lastName:String = txtapellido.text.toString()
            val email:String = txtemail.text.toString()
            val password:String = txtpassword.text.toString()
            val codigo:String = txtcodigo.text.toString()

            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)  && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)  && !TextUtils.isEmpty(codigo) ){

                progressBar.visibility = View.VISIBLE

                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                            task ->

                        if (task.isComplete){
                            val user:FirebaseUser?= auth.currentUser
                            verificarEmail(user)

                            val userBD = dbReferences.child(user!!.uid)


                            userBD.child("Name").setValue(name)
                            userBD.child("lastName").setValue(lastName)
                            llevaralogin()

                        }
                    }
            }

        }

        private fun llevaralogin(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        private fun verificarEmail(user:FirebaseUser?){
            user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                        task ->
                    if (task.isComplete){
                        Toast.makeText(this,"Correo enviado", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Error al enviar Correo ", Toast.LENGTH_LONG).show()
                    }
                }

        }

*/
    }
