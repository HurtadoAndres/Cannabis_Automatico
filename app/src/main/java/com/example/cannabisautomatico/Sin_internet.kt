package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_list__invernaderos.*

class Sin_internet : AppCompatActivity() {
    lateinit var imagen_sinConexion : ImageView
    lateinit var mensaje_cone : TextView
    lateinit var thiscontext : Context
    lateinit var btn_reintentar : TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_internet)
        imagen_sinConexion = findViewById(R.id.sin_conexion)
        mensaje_cone = findViewById(R.id.mensaje_conexion)
        btn_reintentar = findViewById(R.id.reintentar)

        if (container != null) {
            thiscontext = container.context
        }

        btn_reintentar.setOnClickListener {
            is_Hay_Internet()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun is_Hay_Internet(){
        var conexion : Boolean = false
        val connectivityManager =
            thiscontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities: NetworkCapabilities? =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)) {
                    conexion = true
            }
        }else{
            Toast.makeText(thiscontext,"Nose puede conectar, verifique el acceso a internet e Intente nuevamente",
                Toast.LENGTH_LONG).show()

        }
        if (!conexion){
            irhome(conexion)
        }

    }

    fun irhome(conexion:Boolean){
        if (conexion){
            startActivity(Intent(thiscontext,HomeActivity::class.java))
        }
    }


}