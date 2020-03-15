package com.example.cannabisautomatico


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_home.btn_home
import kotlinx.android.synthetic.main.activity_home.btn_mante


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_home.setBackgroundResource(R.drawable.charlar_select)
        val home = Home_fr()
        home.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.contenedor,home).commit()



        btn_home.setOnClickListener{

            btn_home.setBackgroundResource(R.drawable.charlar_select)
            btn_mante.setBackgroundResource(R.drawable.mantenimiento)
            val fr1= Home_fr()
            val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
            trantition1.replace(R.id.contenedor, fr1)
            trantition1.commit()
        }

        btn_mante.setOnClickListener{
            btn_home.setBackgroundResource(R.drawable.charlar)
            btn_mante.setBackgroundResource(R.drawable.mantenimiento_select)
            val fr2= Mantenimiento_fr()
            val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
            trantition1.replace(R.id.contenedor, fr2)
            trantition1.commit()
        }


    }
}
