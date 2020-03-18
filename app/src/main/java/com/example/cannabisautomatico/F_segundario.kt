package com.example.cannabisautomatico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.example.hp.bluetoothjhr.BluetoothJhr


class F_segundario : Fragment() {


    lateinit var  BT : BluetoothJhr //BLUETTOOTH LIBRERIA

    lateinit var modo_manual : Switch
    var modo : String = "false"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_f_segundario, container, false)
        modo_manual = view.findViewById(R.id.M_manual)


        BT = BluetoothJhr(ConexionBT_Activity::class.java,activity )// bluetooth declaracion

            modo_manual.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                   // BT.Tx(modo)

                }
                Toast.makeText(activity,"Modo manual activado",Toast.LENGTH_LONG).show()


            }



        return view
    }

    //----------------------------------CONEXION BLUETOOTH-----------------------------------------


    @Override
    override fun onResume() {
        super.onResume()
        //BT.ConectaBluetooth()
    }


    @Override
    override fun onPause() {
        super.onPause()
       // BT.CierraConexion()
    }
    //-------------------------------------FINAL BLUETOOTH------------------------------------------

    companion object {

        fun newInstance(param1: String, param2: String) =
            F_segundario().apply {

            }
    }
}
