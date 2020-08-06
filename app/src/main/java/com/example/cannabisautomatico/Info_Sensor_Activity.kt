package com.example.cannabisautomatico





import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_info__sensor.*
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class Info_Sensor_Activity: AppCompatActivity() {

    var context = this

    var mhanler = Any()
    var random = Random()
    var lastX: Int = 0

    lateinit var op1: FloatingActionButton
    lateinit var op2: FloatingActionButton
    lateinit var op3: FloatingActionButton

    lateinit var atras: LinearLayout

    private var grafica1: Runnable? = null
    private var thread = Thread()

    var tipo_Sensor :String ? = null
    var codigo_inver : String ? = null
    var KEY_CODIGO = "codigo"
    var URL_SENSOR = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/sensordatos.php"

    var serieData = ArrayList<DataEntry>()
    var numero : Int = 0;
    var series1Mapping : Mapping ? = null
    var series3Mapping : Mapping ? = null

    lateinit var cartesian: Cartesian
    lateinit var anyChartView: AnyChartView
    var dato = ArrayList<Int>()
    lateinit var set: Set


    lateinit var sensor1 : LinearLayout
    lateinit var sensor2 :  LinearLayout
    lateinit var sensor3 :  LinearLayout
    lateinit var sensor_1 : TextView
    lateinit var sensor_2 : TextView
    lateinit var sensor_3 : TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__sensor)
        atras = findViewById(R.id.btn_atras)

        op1 = findViewById(R.id.fab1)
        op2 = findViewById(R.id.fab2)

        sensor1 = findViewById(R.id.sensor1)
        sensor2 = findViewById(R.id.sensor2)
        sensor3 = findViewById(R.id.sensor3)

        sensor_1 = findViewById(R.id.sensor_1)
        sensor_2 = findViewById(R.id.sensor_2)
        sensor_3 = findViewById(R.id.sensor_3)


        atras.setOnClickListener {
            // startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        op2.setOnClickListener {

            var intent = Intent(this,Modo_Manual::class.java)
            intent.putExtra("codigo", codigo_inver)
            startActivity(intent)
        }

        tipo_Sensor = intent.getStringExtra("sensor")
        codigo_inver = intent.getStringExtra("codigo")

        when(tipo_Sensor){
            "temperatura"->{
                sensor_1.text="  Humedad ambiente  "
                sensor_2.text="  Humedad tierra  "
                sensor_3.text="    Ph    "
                sensor1.setOnClickListener{
                    var sensor : String = "humedad_ambiente"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }

                sensor2.setOnClickListener {
                    var sensor : String = "humedad_tierra"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }

                sensor3.setOnClickListener {
                    var sensor : String = "ph"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }


            }
            "humedad_ambiente"->{
                sensor_1.text="  Temperatura  "
                sensor_2.text="  Humedad tierra  "
                sensor_3.text="    Ph    "
                sensor1.setOnClickListener{
                    var sensor : String = "temperatura"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }
                sensor2.setOnClickListener {
                    var sensor : String = "humedad_tierra"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }

                sensor3.setOnClickListener {
                    var sensor : String = "ph"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }
            }
            "ph"->{

                sensor_1.text="  Temperatura  "
                sensor_2.text="  Humedad tierra  "
                sensor_3.text="  Humedad ambiente  "
                sensor1.setOnClickListener{
                    var sensor : String = "temperatura"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }
                sensor2.setOnClickListener {
                    var sensor : String = "humedad_tierra"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }

                sensor3.setOnClickListener {
                    var sensor : String = "humedad_ambiente"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                    finish()
                }

            }
            "humedad_tierra"->{
                sensor_1.text="T  emperatura  "
                sensor_2.text="  Humedad ambiente  "
                sensor_3.text="    Ph    "
                sensor1.setOnClickListener{
                    var sensor : String = "temperatura"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                }
                sensor2.setOnClickListener {
                    var sensor : String = "humedad_ambiente"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                }

                sensor3.setOnClickListener {
                    var sensor : String = "ph"
                    var intent = Intent(this,Info_Sensor_Activity::class.java)
                    intent.putExtra("sensor", sensor)
                    intent.putExtra("codigo", codigo_inver)
                    startActivity(intent)
                }

            }
        }

        set =Set.instantiate()

        anyChartView = findViewById(R.id.any_chart_view)
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar))

        cartesian=AnyChart.line()

        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title(tipo_Sensor)

        cartesian.yAxis(0).title("Datos en tiempo real")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        val series1: Line = cartesian.line(series1Mapping)
        series1.name("Datos ideal")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)


        val series3: Line = cartesian.line(series3Mapping)
        series3.name("Datos actual")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        anyChartView.setChart(cartesian)

        tableroDatosSensor()

        var tiempo = tiempo(this)
        tiempo.execute()
    }


    fun grafica(){

    }


    private class CustomDataEntry internal constructor(
        x: Int?,
        value: Number?,
        value2: Number?,
        value3: Number?
    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }


/*
    fun addEntry(sensor:Double){
        var ramdon = (40 + random.nextInt((60 + 1) - 40)).toDouble()
        lastX++
        //serie.appendData(DataPoint((lastX).toDouble(),sensor), false, 5)
        val values: MutableList<PointValue> = ArrayList<PointValue>()
        values.add(PointValue(lastX.toFloat(), sensor.toFloat()))


        //In most cased you can call data model methods in builder-pattern-like manner.

        //In most cased you can call data model methods in builder-pattern-like manner.
        val line: Line = Line(values).setColor(Color.BLUE).setCubic(true)
        val lines: MutableList<Line> = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.setLines(lines)

        lineChartView!!.setLineChartData(data)
        d_actual.text= "$ramdon°C"
    }

    fun addEntry2(){
        serie2.appendData(DataPoint((lastX).toDouble(),20.0), false, 5)
    }
*/

    fun tableroDatosSensor() {
        var dato = 0.0
        when (tipo_Sensor) {
            "humedad_ambiente" -> {
                d_ideal.text = lastX.toString()
            }
            "temperatura" -> {
                dato = 25.0
                dato.toInt()
                d_ideal.text =
                    "$dato°C" //Promedio de temperatura ya que se reuiere que este entre 23°c  y 27°c
            }
            "ph" -> {
                dato = 6.5
                d_ideal.text = "$dato" //promedio de PH ya que es entre 6.0 y 7.0
            }
            "humedad_tierra" -> {
                d_ideal.text = lastX.toString()
            }
            else -> {
                d_ideal.text = lastX.toString()
            }
        }
        //  serie2.appendData(DataPoint((lastX).toDouble(),dato), false, 10)
    }


    fun SensorDatos() {
        var dato_sensor: Double = 0.0
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_SENSOR,
            Response.Listener { response ->
                val json = JSONObject(response)

                try {
                //  var humedad_tierra = json.getString("humedad_t")
                var humedad_ambiente = json.getString("humedad_a")
                var temperatura = json.getString("temperatura")
                var ph = json.getString("ph")

                when (tipo_Sensor) {
                    "humedad_ambiente" -> {
                        dato_sensor = humedad_ambiente.toDouble()
                    }
                    "temperatura" -> {
                        dato_sensor = temperatura.toDouble()
                    }
                    "ph" -> {
                        dato_sensor = ph.toDouble()
                    }
                    "humedad_tierra" -> {
                        // dato_sensor = humedad_tierra.toDouble()
                    }
                    else -> {
                        dato_sensor = 0.0
                    }
                }
                tableroDatosSensor()
                numero = lastX++

                val serieData: ArrayList<DataEntry> = ArrayList()
                dato.add(dato_sensor.toInt())

                if (dato.size >= 2) {

                    for (i in 0 until dato.size) {
                        serieData.add(
                            CustomDataEntry(
                                i,
                                dato[i],
                                dato[i],
                                dato[i]
                            )
                        )
                    }
                    set.data(serieData)

                }


                d_actual.text=dato_sensor.toString()

               }catch (e:Exception){}

            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_CODIGO] = codigo_inver.toString()


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)


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
        var numeroX: Int = 0;
        var numero = (10 + aleatorio.nextInt((90 + 1) - 10));
        private var activityReference: WeakReference<Info_Sensor_Activity>? = null
        var activity: Info_Sensor_Activity? = null

        var dato_sensor: Double = 0.0


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

            activity!!.SensorDatos()



        }
    }
}

