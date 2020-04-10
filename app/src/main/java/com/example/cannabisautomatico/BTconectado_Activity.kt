package com.example.cannabisautomatico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hp.bluetoothjhr.BluetoothJhr

class BTconectado_Activity : AppCompatActivity() {

    lateinit var  BT : BluetoothJhr
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_tconectado_)

        BT = BluetoothJhr(ConexionBT_Activity::class.java,this)



    }

    @Override
    override fun onResume() {
        super.onResume()
        BT.ConectaBluetooth()
    }


    @Override
    override fun onPause() {
        super.onPause()
        BT.CierraConexion()
    }

}
