package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var usu:String=""
    lateinit var btn_home: LinearLayout
    lateinit var  navegacion : BottomNavigationView
    lateinit var btn_atras : LinearLayout

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navegacion = findViewById(R.id.botonNvegacion)
        btn_home = findViewById(R.id.btn_home)
        btn_atras = findViewById(R.id.btn_atras)

        var numero = intent?.getIntExtra("pagina_fr", 0)
        usu = intent?.getStringExtra("email").toString()
        if (numero == 4){
            paginaAjustes()
        }else if (numero == 3){
            paginaHistorial()
            navegacion.touchscreenBlocksFocus
        }else {
            val invernadero = List_Invernaderos()
            Descripcion?.text = resources.getText(R.string.Invernadero_titulo)
            invernadero.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(R.id.contenedor, invernadero).commit()
        }



        btn_home.setOnClickListener{
            mostrarPerfil()

        }



        navegacion.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    paginaInvernadero()
                    normal()
                }
                R.id.historial -> {
                    paginaHistorial()
                    normal()
                }
                R.id.calendario-> {
                    normal()
                    paginaCalendario()
                }
                R.id.ajustes-> {
                    normal()
                    paginaAjustes()

                }
            }
            return@setOnNavigationItemSelectedListener true
        }



        btn_atras.setOnClickListener {
            paginaInvernadero()
            normal()
        }

    }


    fun normal(){
        tablero_info.gravity = Gravity.NO_GRAVITY
        Descripcion.setPadding(0,0,0,0)
        Titulop.setPadding(0,0,0,0)
        btn_atras.visibility=(View.INVISIBLE)
    }


    fun paginaAjustes(){
        Descripcion.text= resources.getText(R.string.configuracion)
        val fr2= Ajustes_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr2)
        trantition1.commit()
    }

    fun paginaInvernadero(){
        Descripcion?.text= resources.getText(R.string.Invernadero_titulo)
        val fr1= List_Invernaderos()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr1)
        trantition1.commit()
    }


    fun paginaHistorial(){
        Descripcion.text= resources.getText(R.string.Historial)
        val fr_h= historial()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr_h)
        trantition1.commit()
    }

    fun paginaCalendario(){
        Descripcion.text= resources.getText(R.string.Calendario)
        val fr1= calendario()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr1)
        trantition1.commit()
    }

    fun estadoNoCerrarSesion(b:Boolean){
        var preferenfs= getSharedPreferences("archivo_Sesion", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putBoolean("estado.sesion",b)
        editor.apply()
    }

    fun mostrarPerfil() {
        estadoNoCerrarSesion(false)
        startActivity(Intent(this, Perfil::class.java))
    }
}