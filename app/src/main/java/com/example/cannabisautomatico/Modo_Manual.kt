package com.example.cannabisautomatico

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class Modo_Manual : AppCompatActivity() {

    lateinit var modo: Switch
    lateinit var bomba: Switch
    lateinit var luz: Switch
    lateinit var aire: Switch

    var CONTROL_URL = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/Control.php"
    var CONTROLESTADO = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/ConsultaEstadoControl.php"
    var KEY_CODIGO = "codigo"
    var KEY_ESTADO = "estado"
    var KEY_ESTADO2 = "estado_agua"
    var KEY_ESTADO3= "estado_luz"
    var KEY_ESTADO4 = "estado_aire"
    var codigo_inver = ""

    var estado = ""
    var estado2 = ""
    var estado3 = ""
    var estado4 = ""

    lateinit var btn_atras : LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo__manual)
        modo = findViewById(R.id.M_manual)
        bomba = findViewById(R.id.encender_agua)
        luz = findViewById(R.id.encender_luz)
        aire = findViewById(R.id.encender_aire)
        btn_atras = findViewById(R.id.btn_atras)

        codigo_inver = intent.getStringExtra("codigo").toString()
        consultaControl()


        btn_atras.setOnClickListener {
            finish()
        }


        modo.setOnClickListener {
            if (modo.isChecked) {
                estado ="Manual"
                modoControl()
            } else {
                estado="Automatico"
                modoControl()
            }
        }

        bomba.setOnClickListener {
            if (bomba.isChecked) {
                estado2="true"
                modoControl()
            } else {
                estado2="false"
                modoControl()
            }
        }


        luz.setOnClickListener {
            if (luz.isChecked){
                estado3="true"
                modoControl()
                }else{
                estado3="false"
                modoControl()
                }
            }



        aire.setOnClickListener {
            if (aire.isChecked) {
                estado4="true"
                modoControl()
            } else {
                estado4="false"
                modoControl()
            }
        }


    }

    fun modoControl() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, CONTROL_URL,
            Response.Listener { response ->

            }, Response.ErrorListener { error ->

                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_ESTADO] =  estado
                params[KEY_ESTADO2] =  estado2
                params[KEY_ESTADO3] =  estado3
                params[KEY_ESTADO4] =  estado4
                params[KEY_CODIGO] =  codigo_inver


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun consultaControl() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, CONTROLESTADO,
            Response.Listener { response ->
                val json = JSONObject(response)
                var modo = json.getString("modo")
                var aire = json.getString("estado_ai")
                var agua  = json.getString("estado_a")
                var luz = json.getString("estado_l")



                if (modo == "Manual"){
                    this.modo.isChecked = true
                }
                if (aire == "true"){
                    this.aire.isChecked=true
                }

                if (agua == "true"){
                    this.bomba.isChecked=true
                }

                if (luz == "true"){
                    this.luz.isChecked=true
                }


            }, Response.ErrorListener { error ->

                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] =  codigo_inver


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }
}
