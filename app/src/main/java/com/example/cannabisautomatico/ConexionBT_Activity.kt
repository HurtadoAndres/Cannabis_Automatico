package com.example.cannabisautomatico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.example.hp.bluetoothjhr.BluetoothJhr

class ConexionBT_Activity : AppCompatActivity() {

    lateinit var listaBT : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conexion_b_t_)

        listaBT = findViewById(R.id.listaBT)
        val BT : BluetoothJhr=BluetoothJhr(this, listaBT) //mostramos todos los dispositivos vinculados de nuestro celular

        BT.EncenderBluetooth()

        listaBT.setOnClickListener(object : AdapterView.OnItemClickListener,
            View.OnClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                BT.Disp_Seleccionado(view,position,Info_SensorActivity::class.java)
            }

            override fun onClick(v: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
