package com.example.cannabisautomatico

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class pantalla_home_opciones : AppCompatActivity() {
    var titu_e : String ? = null
    var titu_s_e : String ? = null
    var des_e: String ? = null
    var id_e : String ? = null
    var ruta_e : String ? = null
    var numero : Int = 0

    var id_h: String ? = null
    var fecha_h : String ? = null
    var hora_h : String ? = null
    var accion_h : String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_home_opciones)

        val medidaventana = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(medidaventana)
        val ancho : Int = medidaventana.widthPixels
        val alto : Int = medidaventana.heightPixels

        window.setLayout((ancho * 0.85).toInt(),(alto * 0.60).toInt())

        titu_e =  intent.getStringExtra("ti_e")
        titu_s_e = intent.getStringExtra("ti_s_e")
        des_e = intent.getStringExtra("des_e")
        id_e =  intent.getStringExtra("id_e")
        ruta_e =  intent.getStringExtra("rutaIMG_e")
        numero =  intent.getIntExtra("numero", 0)

        id_h =  intent.getStringExtra("id")
        fecha_h = intent.getStringExtra("fecha")
        hora_h = intent.getStringExtra("hora")
        accion_h =  intent.getStringExtra("accion")



         opcionesMostrar(numero)

    }


    fun opcionesMostrar(numero:Int){
        when (numero) {
            0-> {
                paginaEditar()
            }
            1-> {
                paginaCuenta()
            }
            2-> {
                paginaHistorial()
            }
            3 ->{

                paginaNotificaciones(Notificaciones_fr())

            }

            4 ->{

            }
            else ->{

            }
        }
    }

    fun paginaEditar(){
        val editar = editar_fr()
        val args = Bundle()
        args.putString("titulo", titu_e)
        args.putString("titulo_s", titu_s_e)
        args.putString("id", id_e)
        args.putString("ruta_img", ruta_e)
        args.putString("descripcion", des_e)
        editar.setArguments(args)
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, editar)
        trantition1.commit()
    }

    fun paginaCuenta(){
        val cuenta = cuenta_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, cuenta)
        trantition1.commit()
    }
    fun paginaNotificaciones(fragmento : Fragment){
        val opciones = fragmento
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, opciones)
        trantition1.commit()
    }
    fun paginaHistorial(){
        val historial = historial_fr()
        val args = Bundle()
        args.putString("id", id_h)
        args.putString("fecha", fecha_h)
        args.putString("hora", hora_h)
        args.putString("accion", accion_h)
        historial.setArguments(args)
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor,  historial)
        trantition1.commit()
    }
}