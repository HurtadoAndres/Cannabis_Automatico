package com.example.cannabisautomatico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HistorialActivity : AppCompatActivity() {

    lateinit var  btn_home:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        btn_home=findViewById(R.id.btn_home)

        btn_home.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
}
