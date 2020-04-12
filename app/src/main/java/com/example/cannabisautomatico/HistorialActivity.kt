package com.example.cannabisautomatico


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class HistorialActivity : AppCompatActivity() , AdapterView.OnItemClickListener {

    lateinit var  btn_home:Button
    var contactList = ArrayList<Any>()
    lateinit var lv:ListView
    var usuario:String=""

    var idJ:String=""
    var fechaJ:String=""
    var horaJ:String=""
    var accionesJ:String=""

    var mlista : List<HistorialCl> = ArrayList()
    lateinit var madapter : ListaAdapter

   // val contact: HashMap<String, String> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        btn_home=findViewById(R.id.btn_home)
        lv=findViewById(R.id.HistorialList)
        consultaHistorial()

        contactList = ArrayList<Any>()

        btn_home.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        val intent= intent.extras

       if (intent!= null) {
           usuario= intent.getString("email").toString()
       }


    }
    fun getDataFragment(): String{

        return usuario;

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    fun consultaHistorial(){
            var nodata = findViewById<TextView>(R.id.Nodata)
            var response =  Response.Listener<String> { response ->

                val json = JSONObject(response)
                var success = json.getBoolean("success")

                if (success) {

                } else {
                    nodata.visibility=View.INVISIBLE
                try {
                    val jsonarray = json.getJSONArray("historial")


                        for (i in 0 until jsonarray.length()) {
                            Toast.makeText(this, "a$i", Toast.LENGTH_LONG).show()
                            var jsonA = jsonarray.getJSONObject(i)
                            idJ = jsonA.getString("codigo")
                            fechaJ = jsonA.getString("fecha")
                            horaJ = jsonA.getString("hora")
                            accionesJ = jsonA.getString("acciones")
                            // contactList.add("Id : $idJ\nHora : $horaJ\nFecha : $fechaJ\nAcciones= $accionesJ")
                            (mlista as ArrayList<HistorialCl>).add(
                                HistorialCl(
                                    "Id : $idJ",
                                    "Hora : $horaJ",
                                    "Fecha : $fechaJ",
                                    "Acciones= $accionesJ"
                                )
                            )
                        }

                        cargarLisview()


                } catch (e: JSONException) {
                    Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show()
                }
            }

            }
            var registro = HistorialRequest(usuario,response)
            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(registro)


    }

    fun cargarLisview(){
       // var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
       // lv.adapter=adapter

        madapter =  ListaAdapter(this,R.layout.prest_historial, mlista as ArrayList<HistorialCl>)
        lv.adapter=madapter
    }



}





