package com.example.cannabisautomatico


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity



class AnimacionActivity : AppCompatActivity() {

       private lateinit var  handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animacion)

        handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@AnimacionActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }


}
