package com.example.cannabisautomatico



import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.ref.WeakReference
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
    var KEY_ESTADO3 = "estado_luz"
    var KEY_ESTADO4 = "estado_aire"
    var codigo_inver = ""
    var URL_SENSOR = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/sensordatos.php"

    var estado = ""
    var estado2 = ""
    var estado3 = ""
    var estado4 = ""

    lateinit var btn_atras: LinearLayout
    lateinit var hum_t : TextView
    lateinit var hum_a : TextView
    lateinit var temp : TextView
    lateinit var ph : TextView



    private lateinit var notificationM : NotificationManagerCompat;
    private var despcricion :String = "Se le notificara el estado \nde su invernadero"
    var actualFecha = Calendar.getInstance()
    var calendar = Calendar.getInstance()
    private var año : Int = 0
    private var mes : Int = 0
    private var dia : Int = 0
    private var hora : Int = 0
    private var minuto : Int = 0

    var nombretitulo : String = ""
    var titulo = "Modo $estado activado"
    var descripcion = "Modo cambiado en tu invernadero $nombretitulo"

    private var pendingIntent: PendingIntent? = null
    private val CHANNEL_ID = "NOTIFICACION"
    private val NOTIFICACION_ID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo__manual)
        modo = findViewById(R.id.M_manual)
        bomba = findViewById(R.id.encender_agua)
        luz = findViewById(R.id.encender_luz)
        aire = findViewById(R.id.encender_aire)
        btn_atras = findViewById(R.id.btn_atras)
        hum_a = findViewById(R.id.hum_a)
        hum_t = findViewById(R.id.hum_t)
        temp = findViewById(R.id.temp)
        ph = findViewById(R.id.ph)

        notificationM = NotificationManagerCompat.from(this)

        codigo_inver = intent.getStringExtra("codigo").toString()
        nombretitulo =  intent.getStringExtra("titulo").toString()
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


            setPendingIntent()
            createNotificationChannel();
            createNotification();

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

        var tiempo = tiempoModo(this)
        tiempo.execute()
    }

    private fun setPendingIntent() {
        val intent = Intent(this, Modo_Manual::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(Modo_Manual::class.java)
        stackBuilder.addNextIntent(intent)
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Noticacion"
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
        builder.setSmallIcon(R.drawable.hoja)
        builder.setContentTitle(title)
        builder.setContentText(descripcion)
        builder.color = Color.BLUE
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setLights(Color.MAGENTA, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(Notification.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build())
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
                var agua = json.getString("estado_a")
                var luz = json.getString("estado_l")



                if (modo == "Manual") {
                    this.modo.isChecked = true
                }
                if (aire == "true") {
                    this.aire.isChecked = true
                }

                if (agua == "true") {
                    this.bomba.isChecked = true
                }

                if (luz == "true") {
                    this.luz.isChecked = true
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
    fun SensorDatos() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_SENSOR,
            Response.Listener { response ->
                val json = JSONObject(response)
                try {

                    var humedad_T = json.getString("humedad_t")
                    var temperatura = json.getString("temperatura")
                    var humedad_A = json.getString("humedad_a")
                    var ph = json.getString("ph")
                    this.hum_a.text = humedad_A
                    this.hum_t.text = humedad_T
                    this.temp.text = temperatura
                    this.ph.text = ph

                } catch (e: Exception) {
                    Toast.makeText(this, "$e aqui", Toast.LENGTH_LONG).show()

                }

            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG).show()
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
        var tiempo = tiempoModo(this)
        tiempo.execute()

    }

    class tiempoModo : AsyncTask<Void, Integer, Boolean> {

        var numeroX: Int = 0;
        private var activityReference: WeakReference<Modo_Manual>? = null
        var activity: Modo_Manual? = null


        constructor(context: Modo_Manual) : super() {
            activityReference = WeakReference<Modo_Manual>(context)
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
