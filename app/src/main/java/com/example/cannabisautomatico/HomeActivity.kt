package com.example.cannabisautomatico


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*


open class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    var menuInteration:Boolean=true
    var usua:String=""
    lateinit var layoutopciones : LinearLayout
    lateinit var opciones : LinearLayout
    lateinit var  navegacion : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        layoutopciones = findViewById(R.id.mas_opciones)
  //      opciones = findViewById(R.id.opciones)
        navegacion = findViewById(R.id.botonNvegacion)



        val invernadero = List_Invernaderos()
        Descripcion?.text= resources.getText(R.string.Invernadero_titulo)
        invernadero.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.contenedor,invernadero).commit()



        btn_home.setOnClickListener{

            if (menuInteration){
                menu(it)
            }else{
                paginaHome()
                menuInteration=true
            }

        }


            navegacion.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        paginaInvernadero()
                    }
                    R.id.historial -> {
                        paginaHistorial()
                    }
                    R.id.calendario-> {
                        paginaCalendario()
                    }
                    R.id.ajustes-> {
                       paginaAjustes()

                    }
                }
                return@setOnNavigationItemSelectedListener true
            }


    }

   fun menu(v: View){

      var m = PopupMenu(this, v)
      m.setOnMenuItemClickListener(this)
       m.inflate(R.menu.popup_menu)
       m.show()
   }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){

            R.id.Perfil->
            {
                mostrarPerfil()
                 menuInteration=false

                 return true
            }

            R.id.Sesion->
            {
                estadoNoCerrarSesion(false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return true
            }

            else->{

                return false
            }
        }

    }

    fun paginaAjustes(){
        Descripcion.text= resources.getText(R.string.configuracion)
        val fr2= Ajustes_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr2)
        trantition1.commit()
    }

    fun paginaHome(){
        Descripcion.text= resources.getText(R.string.Info_sensores)
        val fr1= Home_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr1)
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
