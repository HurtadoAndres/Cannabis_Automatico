package com.example.cannabisautomatico


import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_home.*


open class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    var menuInteration:Boolean=true
    var usuario:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val home = Home_fr()
        home.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.contenedor,home).commit()

        val bundle = intent.extras
        if (bundle != null) {
            usuario= bundle.getString("email")
        }




        btn_mante.setOnClickListener{

            if (menuInteration){
                menu(it)
            }else{
                paginaHome()
                menuInteration=true
            }

        }

        mas_opciones.setOnClickListener{

                paginaMasOpciones()
                mas_opciones.visibility=(View.INVISIBLE);

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

    fun paginaMasOpciones(){
        val fr1= MasOpciones()
        val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
        trantition1.replace(R.id.opciones, fr1)
        trantition1.commit()
    }
    fun verda(){
        mas_opciones.visibility=(View.VISIBLE);
    }



}
