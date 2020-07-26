package com.example.cannabisautomatico


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity



class AnimacionActivity : AppCompatActivity() {

       private lateinit var  handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animacion)

        if (obtenerEstadoNoCerrarSesion()){
            startActivity(Intent(this@AnimacionActivity, HomeActivity::class.java))
            finish()
        }else{
            handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this@AnimacionActivity, BienvenidoActivity::class.java))
                finish()
            }, 3000)
        }




    }

     fun obtenerEstadoNoCerrarSesion():Boolean{
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        return  preferenfs.getBoolean("estado.sesion",false)
    }


}
