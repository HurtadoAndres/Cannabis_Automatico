package com.example.cannabisautomatico





import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentTransaction
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.getbase.floatingactionbutton.FloatingActionButton
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_info__sensor.*
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.thread


class Info_Sensor_Activity: AppCompatActivity() {

    var context = this
    lateinit var graphview: GraphView
    lateinit var serie: LineGraphSeries<DataPoint>
    lateinit var serie2: LineGraphSeries<DataPoint>
    var mhanler = Any()
    var random = Random()
    var lastX : Int = 0

    lateinit var op1 : FloatingActionButton
    lateinit var op2 : FloatingActionButton
    lateinit var op3 : FloatingActionButton

    lateinit var atras : LinearLayout

    var URL_SENSOR : String = ""
    var KEY_CODIGO : String = ""
    var tipo_Sensor : String = ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__sensor)
        atras = findViewById(R.id.btn_atras)

        op1 = findViewById(R.id.fab1)
        op2 = findViewById(R.id.fab2)

        tipo_Sensor = intent?.getStringExtra("sensor").toString()


        atras.setOnClickListener {
           // startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }



        graphview = findViewById(R.id.graph)
        serie = LineGraphSeries<DataPoint>()
        serie2 = LineGraphSeries<DataPoint>()
        graphview.addSeries(serie)
        graphview.addSeries(serie2)

        serie.color= getColor(R.color.color1)
        serie.thickness = 12
        serie.isDrawDataPoints = true
        serie.isDrawBackground=true
        serie.backgroundColor=getColor(R.color.color1_1)
        serie.dataPointsRadius = 15f

        serie2.color= getColor(R.color.color3)
        serie2.thickness = 12
        serie2.isDrawBackground=true
        serie2.backgroundColor=getColor(R.color.color3_1)
        serie2.isDrawDataPoints = true
        serie2.dataPointsRadius = 15f

        val viewport = graph.viewport
        viewport.setMinY(20.0)
        viewport.setMaxY(60.0)
        viewport.isYAxisBoundsManual = true




        //    addRandomDataPonint()


        var tiempo = tiempo(this)
        tiempo.execute()
    }


    fun addEntry(ramdon:Double){
        //var ramdon = (40 + random.nextInt((60 + 1) - 40)).toDouble()
        lastX++
        serie.appendData(DataPoint((lastX).toDouble(),ramdon), false, 10)
        d_actual.text=ramdon.toString()
    }

    fun addEntry2(){
        serie2.appendData(DataPoint((lastX).toDouble(),46.0), false, 10)
    }

    fun tableroDatosSensor() {
        when (tipo_Sensor) {
            "humedad" -> {
                d_ideal.text = lastX.toString()
            }
            "temperatura" -> {
                d_ideal.text = "25°C" //Promedio de temperatura ya que se reuiere que este entre 23°c  y 27°c
            }
            "ph" -> {
                d_ideal.text = "6.5" //promedio de PH ya que es entre 6.0 y 7.0
            }
            "humedad_t" -> {
                d_ideal.text = lastX.toString()
            }
            else -> {
                d_ideal.text = lastX.toString()
            }
        }
    }


/*
    override fun onResume() {
        super.onResume()
        var num : Int = 1

       mhanler = Thread(Runnable {
            // we add 100 new entries

            for (i in 0..99) {

                runOnUiThread {
                    //addEntry()
                    //addEntry2()
                }

                // sleep to slow down the add of entries
                try {
                    Thread.sleep(800)
                } catch (e: InterruptedException) {
                    // manage error ...
                }

            }

        }).start()

    }

 */

    fun SensorDatos():Double {
        var dato_sensor : Double = 0.0
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_SENSOR,
            Response.Listener { response ->
                val json = JSONObject(response)
                var humedad_tierra = json.getString("humedad_t")
                var humedad_ambiente = json.getString("humedad_a")
                var temperatura = json.getString("temperatura")
                var ph = json.getString("ph")

               when(tipo_Sensor){
                   "humedad"->
                           {
                               dato_sensor = humedad_ambiente.toDouble()
                           }
                   "temperatura"->
                           {
                               dato_sensor = temperatura.toDouble()
                           }
                   "ph"->
                           {
                               dato_sensor = ph.toDouble()
                           }
                   "humedad_t"->
                           {
                               dato_sensor = humedad_tierra.toDouble()
                           }
                   else->{
                                dato_sensor = 0.0
                   }
               }


            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] =


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)


        return dato_sensor

    }



    fun hilo() {
        try {
            Thread.sleep(1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun ejecutar() {
        var tiempo = tiempo(this)
        tiempo.execute()

    }

    class tiempo : AsyncTask<Void, Integer, Boolean> {
        val aleatorio = Random();
        var numeroX:Int = 0;
        var numero = (10 + aleatorio.nextInt((90 + 1) - 10));
        private var activityReference: WeakReference<Info_Sensor_Activity>? = null
        var activity: Info_Sensor_Activity? = null

        var dato_sensor:Double=0.0


        constructor(context: Info_Sensor_Activity) : super() {
            activityReference = WeakReference<Info_Sensor_Activity>(context)
            activity = activityReference!!.get()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            for (i in 1..3) {
                activity?.hilo()
                numeroX++

            }

            return true
        }

        override fun onPostExecute(result: Boolean?) {
            activity?.ejecutar()

               var ramdon = (40 + activity!!.random.nextInt((60 + 1) - 40)).toDouble()
              //dato_sensor = activity!!.SensorDatos()
              //activity!!.addEntry(dato_sensor)
              //activity!!.tableroDatosSensor()
              activity!!.addEntry(ramdon)
              activity!!.addEntry2()

        }
    }

    fun obtenerInvernaderoClick():Int{
        var preferenfs= getSharedPreferences("Invernadero", Context.MODE_PRIVATE)
        return  preferenfs.getInt("invernaderoClick",0)
    }


}
