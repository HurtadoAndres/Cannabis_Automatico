package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class HistorialActivity : AppCompatActivity() {

    lateinit var  btn_home:Button
    var contactList = ArrayList<Any>()
    lateinit var lv:ListView
    var usuario:String?=null
    lateinit  var fecha : TextView
    lateinit var hora :TextView
    lateinit var id :TextView
    lateinit var acciones :TextView
    lateinit var lista :String

    val contact: HashMap<String, String> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        btn_home=findViewById(R.id.btn_home)
        lv=findViewById(R.id.HistorialList)


        contactList = ArrayList<Any>()

        btn_home.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }


        consultaHistorial()

        val bundle = intent.extras
        if (bundle != null) {
            usuario= bundle.getString("email")
        }


    }

    fun consultaHistorial(){

            var response =  Response.Listener<String> { response ->

                try {

                    val json = JSONObject(response)
                    Toast.makeText(this,"Hola $json", Toast.LENGTH_LONG).show()
                    val jsonarray = json.getJSONArray("Array")
                    Toast.makeText(this,"Hola $jsonarray", Toast.LENGTH_LONG).show()

                   // var success = json.getBoolean("success")


                        for (i in 0..jsonarray.length()) {
                            var jsonob = jsonarray.getJSONObject(i)
                            var idJ = jsonob.getString("codigo")
                            var fechaJ = jsonob.getString("fecha")
                            var horaJ = jsonob.getString("hora")
                            var accionesJ = jsonob.getString("acciones")
                            lista = idJ + "" + horaJ + "" + fechaJ + "" + accionesJ
                            contactList.add(lista)
                            Toast.makeText(this,"Hola $fechaJ", Toast.LENGTH_LONG).show()
                        }

                        /*
                            for (i in 0 until json.length() step  3){


                              fecha = json.getString("fecha")
                                hora=json.getString("hora")
                                contact.put("Id", id)
                                contact.put("Hora", hora)
                                contact.put("Fecha", fecha)

                                contactList.add(contact)


                            }
                            */




                        cargarLisview()
                        //cargar()


                }catch (e: JSONException){}

            }
            var registro = HistorialRequest("hurtadoandres942@gmail.com",response)
            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(registro)


    }

    fun cargarLisview(){
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
        lv.adapter=adapter
    }



}





