package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home_fr.*


class Home_fr : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_home_fr, container, false)

        val btn_h = view.findViewById<Button>(R.id.btn_h)
        val btn_t = view.findViewById<Button>(R.id.btn_t)
        val btn_l = view.findViewById<Button>(R.id.btn_l)
        val btn_p = view.findViewById<Button>(R.id.btn_p)

        btn_h.setOnClickListener{
            startActivity(Intent(activity,Info_SensorActivity::class.java))
        }
        btn_t.setOnClickListener{
            startActivity(Intent(activity,Info_SensorActivity::class.java))
        }
        btn_l.setOnClickListener{
            startActivity(Intent(activity,Info_SensorActivity::class.java))
        }
        btn_p.setOnClickListener{
            startActivity(Intent(activity,Info_SensorActivity::class.java))
        }

        return view
    }


    companion object {

        fun newInstance(param1: String, param2: String) =
            Home_fr().apply {

            }
    }
}
