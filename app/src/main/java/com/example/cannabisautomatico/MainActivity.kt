package com.example.cannabisautomatico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtemail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()


        btn_registrar.setOnClickListener{
            val intent:Intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        btn_recuperar_p.setOnClickListener{
            val intent:Intent = Intent(this, Olvide_passwordActivity::class.java)
            startActivity(intent)
        }

        btn_iniciar.setOnClickListener{
            loginUser()
        }
    }

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

    private fun IrAlHome(){
        startActivity(Intent(this,HomeActivity::class.java))
    }
}
