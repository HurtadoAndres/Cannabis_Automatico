package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class BienvenidoActivity : AppCompatActivity() {

   lateinit var btn_registrar : Button
  lateinit  var btn_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenido)

        btn_registrar = findViewById(R.id.btn_registrar)
        btn_login = findViewById(R.id.btn_iniciar)

        btn_registrar.setOnClickListener{
            val intent: Intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener{
            val intent:Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }






}
