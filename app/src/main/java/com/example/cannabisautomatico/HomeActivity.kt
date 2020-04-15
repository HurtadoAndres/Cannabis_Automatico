package com.example.cannabisautomatico


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_home.*


open class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    var menuInteration:Boolean=true
    var usua:String=""
   lateinit var layoutopciones : LinearLayout
    lateinit var opciones : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        layoutopciones = findViewById(R.id.mas_opciones)
        opciones = findViewById(R.id.opciones)
        val historial=findViewById<LinearLayout>(R.id.Historial)




        val home = Home_fr()
        home.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.contenedor,home).commit()



        btn_mante.setOnClickListener{

            if (menuInteration){
                menu(it)
            }else{
                paginaHome()
                menuInteration=true
            }

        }

//----------------boton ocultar mas ocpiones---------------------------
        layoutopciones.y= 163F
        opciones.y=150F
        var movimiento : Int = 0

        mas_opciones.setOnClickListener{

            if (movimiento == 0) {
                layoutopciones.y = 73F
                opciones.y = 190F
                movimiento = 1
            }else{
                layoutopciones.y= 240F
                opciones.y=380F
                movimiento = 0
            }

        }
        historial.setOnClickListener{
            val intent = Intent(this,HistorialActivity::class.java)
            intent.putExtra("email",usua)
            startActivity(intent)
        }

//-------------------final---------------------------------------------------



    }

   fun menu(v: View){

      var m = PopupMenu(this, v)
      m.setOnMenuItemClickListener(this)
       m.inflate(R.menu.popup_menu)
       m.show()
   }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){

            R.id.Ajustes->
            {
                paginaMantenimiento()
                 menuInteration=false

                 return true
            }

            R.id.Sesion->
            {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }

            else->{
                btn_mante.setBackgroundResource(R.drawable.mantenimiento_s)
                return false
            }
        }

    }

    fun paginaMantenimiento(){
        btn_mante.setBackgroundResource(R.drawable.flecha)
        Descripcion.text= resources.getText(R.string.configuracion)
        val fr2= Mantenimiento_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr2)
        trantition1.commit()
    }

    fun paginaHome(){
        btn_mante.setBackgroundResource(R.drawable.mantenimiento_s)
        Descripcion.text= resources.getText(R.string.Info_sensores)
        val fr1= Home_fr()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.contenedor, fr1)
        trantition1.commit()
    }




}
