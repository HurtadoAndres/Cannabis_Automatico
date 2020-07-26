package com.example.cannabisautomatico

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.airbnb.lottie.model.DocumentData

class Acerca_de : AppCompatActivity() {
    lateinit var acerca :TextView
    lateinit var btn_atras :LinearLayout
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        btn_atras = findViewById(R.id.btn_atras)
        acerca = findViewById(R.id.acerca)
        acerca.justificationMode = JUSTIFICATION_MODE_INTER_WORD

        btn_atras.setOnClickListener {
            finish()
        }
    }
}