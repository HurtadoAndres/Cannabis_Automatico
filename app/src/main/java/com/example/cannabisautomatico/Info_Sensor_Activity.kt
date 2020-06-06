package com.example.cannabisautomatico





import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.getbase.floatingactionbutton.FloatingActionButton
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_info__sensor.*
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

    lateinit var atras : Button

    private var grafica1: Runnable? = null
    private var thread = Thread()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__sensor)
        atras = findViewById(R.id.btn_home)

        op1 = findViewById(R.id.fab1)
        op2 = findViewById(R.id.fab2)


        atras.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
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


    fun addEntry(){
        var ramdon = (40 + random.nextInt((60 + 1) - 40)).toDouble()
        lastX++
        serie.appendData(DataPoint((lastX).toDouble(),ramdon), false, 5)
        d_ideal.text=lastX.toString()
        d_actual.text=ramdon.toString()
    }

    fun addEntry2(){
        serie2.appendData(DataPoint((lastX).toDouble(),46.0), false, 5)
    }



    override fun onResume() {
        super.onResume()
        var num : Int = 1

       mhanler = Thread(Runnable {
            // we add 100 new entries

            for (i in 0..99) {

                runOnUiThread {
                    addEntry()
                    addEntry2()
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

    override fun onStop() {
        super.onStop()

    }


    fun update(numero: Int) {

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
            activity?.update(numero)


            //  activity?.addEntry(numeroX.toDouble(),numero.toDouble())



        }
    }

    fun obtenerInvernaderoClick():Int{
        var preferenfs= getSharedPreferences("Invernadero", Context.MODE_PRIVATE)
        return  preferenfs.getInt("invernaderoClick",0)
    }


}
