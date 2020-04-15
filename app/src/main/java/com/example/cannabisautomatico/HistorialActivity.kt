package com.example.cannabisautomatico


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
    var usuarioId:String=""

    var idJ:String=""
    var fechaJ:String=""
    var horaJ:String=""
    var accionesJ:String=""

    var mlista : List<HistorialCl> = ArrayList()
    lateinit var madapter : ListaAdapter
    private lateinit var  handler : Handler

    lateinit var fondo_cargando:LinearLayout

    // val contact: HashMap<String, String> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        btn_home=findViewById(R.id.btn_home)
        lv=findViewById(R.id.HistorialList)
        fondo_cargando = findViewById(R.id.fondo_cargando)
        consultaHistorial()

        contactList = ArrayList<Any>()

        btn_home.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

       handler = Handler()
       handler.postDelayed({
           fondo_cargando.visibility=(View.INVISIBLE)

       }, 3000)



    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
    fun cargar_UusarioPassword(){
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        var usuario : String? = preferenfs.getString("email", "")
        usuarioId=usuario.toString()




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
                            var jsonA = jsonarray.getJSONObject(i)
                            idJ = jsonA.getString("codigo")
                            fechaJ = jsonA.getString("fecha")
                            horaJ = jsonA.getString("hora")
                            accionesJ = jsonA.getString("acciones")
                            // contactList.add("Id : $idJ\nHora : $horaJ\nFecha : $fechaJ\nAcciones= $accionesJ")
                            (mlista as ArrayList<HistorialCl>).add(
                                HistorialCl(
                                    idJ,
                                    horaJ,
                                    fechaJ
                                )
                            )
                        }

                        cargarLisview()


                } catch (e: JSONException) {
                    Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show()
                }
            }

            }
            cargar_UusarioPassword()
            var registro = HistorialRequest(usuarioId,response)
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





