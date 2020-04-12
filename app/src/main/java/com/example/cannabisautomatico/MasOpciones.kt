package com.example.cannabisautomatico

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class MasOpciones : Fragment() {

   var verda=0
    var usuario:String?=null
   lateinit var  mas : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_mas_opciones, container, false)
        val mas_opcionesFr=view.findViewById<LinearLayout>(R.id.mas_opciones)
        val pciones=view.findViewById<LinearLayout>(R.id.opciones)
        val historial=view.findViewById<LinearLayout>(R.id.Historial)

        mas_opcionesFr.setOnClickListener{

            mas_opcionesFr.visibility=(View.INVISIBLE);
            pciones.visibility=(View.INVISIBLE);

            (activity as HomeActivity?)?.verda()
            usuario= (activity as HistorialActivity?)?.getDataFragment()


        }

        historial.setOnClickListener{
            val intent = Intent(activity,HistorialActivity::class.java)
            intent.putExtra("usuario",usuario)
            startActivity(intent)
        }




        return view
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MasOpciones().apply {
                val home = HomeActivity()
                if (verda==1){
                    home.verda()
                }

            }
    }

}
