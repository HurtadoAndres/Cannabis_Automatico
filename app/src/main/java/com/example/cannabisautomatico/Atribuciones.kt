package com.example.cannabisautomatico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class Atribuciones : AppCompatActivity() {
    lateinit var btn_atras :LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atribuciones)
        btn_atras = findViewById(R.id.btn_atras)

        btn_atras.setOnClickListener {
            finish()
        }
    }
}