package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_registrar.*

class Perfil : AppCompatActivity() {

    lateinit var imagen : ImageView
    var imagenSiguiente : Int = 0
    var usuarioId: String = ""
    lateinit var nombre_P : TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        imagen = findViewById(R.id.imageView)
        nombre_P = findViewById(R.id.nombre_p)

        val medidaventana = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(medidaventana)
        val ancho : Int = medidaventana.widthPixels
        val alto : Int = medidaventana.heightPixels

        window.setLayout((ancho * 0.95).toInt(),(alto * 0.70).toInt())


        mostrarPerfil()
        cargar_UusarioPassword()
        nombre_P.text=obtenerNombreApellido()

        imagenSiguiente = obtenerEstadoImagen()
        if (imagenSiguiente==1){
            imagen.setBackgroundResource(R.drawable.burro)
        }else if (imagenSiguiente == 2){
            imagen.setBackgroundResource(R.drawable.panda)
        }else{
            imagen.setBackgroundResource(R.drawable.logo_actual)
        }

        imagen.setOnClickListener {
            imagenSiguiente++
            estadoImagen(imagenSiguiente)
            if (imagenSiguiente==1){
                imagen.setBackgroundResource(R.drawable.burro)

            }else if (imagenSiguiente == 2){
                imagen.setBackgroundResource(R.drawable.panda)
            }else{
                imagen.setBackgroundResource(R.drawable.logo_actual)
                imagenSiguiente=0
            }

        }

    }

    fun estadoNoCerrarSesion(b:Boolean){
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putBoolean("estado.sesion",b)
        editor.apply()
    }

    fun obtenerNombreApellido(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var nombre = preferenfs.getString("nombre","")
        var apellido =  preferenfs.getString("apellido","")
        return "$nombre $apellido"
    }

    fun mostrarPerfil() {
        val salir: TextView
        val sesion: LinearLayout

        salir=findViewById(R.id.salir)
        sesion = findViewById(R.id.Sesion)
        salir.setOnClickListener {
            finish()
        }
        sesion.setOnClickListener{
            estadoNoCerrarSesion(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    fun cargar_UusarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var usuario : String? = preferenfs.getString("email", "")
        usuarioId=usuario.toString()
    }
    fun estadoImagen(imagen : Int){
        var preferenfs= getSharedPreferences(usuarioId, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putInt("imagen", imagen)
        editor.apply()
    }
    fun obtenerEstadoImagen(): Int {
        var preferenfs= getSharedPreferences(usuarioId, Context.MODE_PRIVATE)
        return  preferenfs.getInt("imagen", 0)
    }
}
