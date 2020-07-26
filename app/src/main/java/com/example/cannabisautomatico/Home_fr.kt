package com.example.cannabisautomatico


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home_fr.*


class Home_fr : Fragment() {

 var expande :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_home_fr, container, false)

        val btn_h = view.findViewById<LinearLayout>(R.id.btn_h)
        val btn_t = view.findViewById<LinearLayout>(R.id.btn_t)
        val btn_l = view.findViewById<LinearLayout>(R.id.btn_l)
        val btn_p = view.findViewById<LinearLayout>(R.id.btn_p)
        val btn_m = view.findViewById<LinearLayout>(R.id.btn_m)
        val btn_c = view.findViewById<LinearLayout>(R.id.btn_c)


        btn_h.setOnClickListener{
           var sensor : String = "humedad"
           var intent = Intent(activity,Info_Sensor_Activity::class.java)
               intent.putExtra("sensor", sensor)
               startActivity(intent)
        }
        btn_t.setOnClickListener{
            var sensor : String = "temperatura"
            var intent = Intent(activity,Info_Sensor_Activity::class.java)
            intent.putExtra("sensor", sensor)
            startActivity(intent)
        }
        btn_l.setOnClickListener{
            var sensor : String = "humedad_t"
            var intent = Intent(activity,Info_Sensor_Activity::class.java)
            intent.putExtra("sensor", 1)
            startActivity(intent)
        }
        btn_p.setOnClickListener{
            var sensor : String = "ph"
            var intent = Intent(activity,Info_Sensor_Activity::class.java)
            intent.putExtra("sensor", sensor)
            startActivity(intent)
        }

        btn_c.setOnClickListener{
            //startActivity(Intent(activity,Info_Sensor_Activity::class.java))
        }

        btn_m.setOnClickListener{
            //startActivity(Intent(activity,Info_Sensor_Activity::class.java))
        }

        return view
    }


    companion object {

        fun newInstance(param1: String, param2: String) =
            Home_fr().apply {

            }
    }
}
