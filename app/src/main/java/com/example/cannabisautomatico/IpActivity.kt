package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.anychart.standalones.axismarkers.Text
import kotlinx.android.synthetic.main.activity_ip.*

class IpActivity : AppCompatActivity() {
    lateinit var ip : TextView
    lateinit var btn_ir : LinearLayout
    lateinit var btn_borrar : TextView
    lateinit var textIp : TextView
    var tamaño_alto : Double = 0.20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip)

        ip = findViewById(R.id.Ip)
        btn_ir = findViewById(R.id.btn_ir)
        btn_borrar = findViewById(R.id.ipborrar)
        textIp = findViewById(R.id.ipsave)


        val medidaventana = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(medidaventana)
        val ancho : Int = medidaventana.widthPixels
        val alto : Int = medidaventana.heightPixels



        if (ipObtener() != null && ipObtener() != "") {
            tamaño_alto = 0.27
            textIp.text=ipObtener()

        }else{
            tamaño_alto = 0.20
        }

        window.setLayout((ancho * 0.85).toInt(),(alto * tamaño_alto).toInt())
        if (ipObtener() != null && ipObtener() != "") iptablero.visibility=(View.VISIBLE)
        else  iptablero.visibility=(View.INVISIBLE)

        btn_borrar.setOnClickListener{
            if (ipObtener() != null && ipObtener() != ""){
                ipSave("")
                textIp.text=""
                iptablero.visibility=(View.INVISIBLE)
                startActivity(Intent(this,IpActivity::class.java))
            }
        }

        btn_ir.setOnClickListener {
            val intent = Intent(this,CamaraWebActivity::class.java)
            intent.putExtra("ip", ip.text.toString())
            startActivity(intent)
            ipSave(ip.text.toString())
            finish()
        }
    }

    fun ipSave(ip : String){
        var preferenfs= getSharedPreferences("ip", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = preferenfs.edit()
        editor.putString("ip", ip)
        editor.apply()
    }

    fun ipObtener(): String? {
        var preferenfs= getSharedPreferences("ip", Context.MODE_PRIVATE)
        return  preferenfs?.getString("ip","")
    }

}