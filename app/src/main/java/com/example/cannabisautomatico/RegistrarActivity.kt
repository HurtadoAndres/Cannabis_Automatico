package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrar.*

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


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registrar)

            //Buscamos las vistas (elementos creados en la vista)
            txtnombre = findViewById(R.id.nombre)
            txtapellido = findViewById(R.id.apellido)
            txtemail = findViewById(R.id.email)
            txtpassword = findViewById(R.id.password)
            txtcodigo = findViewById(R.id.codigo)

            progressBar = findViewById(R.id.progressBar)

            dataBase = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()

            dbReferences = dataBase.reference.child("User")

            btn_registrar.setOnClickListener{
                Create_nueva_cuenta()
            }

        }



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
    }
