package com.example.cannabisautomatico

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.util.*

class calendario : Fragment() {
 lateinit  var txt:TextView
    lateinit var btn_fecha : LinearLayout
    lateinit var btn_hora : LinearLayout
    lateinit var txt_fecha : EditText
    lateinit var txt_hora : EditText

    var dia : Int = 0
    var mes : Int = 0
    var año : Int = 0
    var hora : Int = 0
    var minutos : Int = 0
    lateinit var thiscontext : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendario, container, false)
        btn_fecha = view.findViewById(R.id.btn_fecha)
        btn_hora = view.findViewById(R.id.btn_hora)
        txt_fecha = view.findViewById(R.id.txt_fecha)
        txt_hora = view.findViewById(R.id.txt_hora)

        if (container != null) {
            thiscontext = container.context
        }


        btn_fecha.setOnClickListener {
            fecha_hora("fecha")
        }

        btn_hora.setOnClickListener {
            fecha_hora("hora")
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun fecha_hora(opcion:String){
        var calendario = Calendar.getInstance()
        var todo : String = ""
        when(opcion){
            "fecha"->{
                dia=calendario.get(Calendar.DAY_OF_MONTH)
                mes=calendario.get(Calendar.MONTH)
                año=calendario.get(Calendar.YEAR)

                todo = "$dia/$mes/$año"
                var datePicker = DatePickerDialog(thiscontext,
                    OnDateSetListener { view, year, month, dayOfMonth ->
                        var mess: Int = month+1
                        todo="$dayOfMonth/$mess/$year"
                        txt_fecha.setText(todo)
                    }, dia, mes, año)
                datePicker.show()
            }
            "hora"-> {
                hora=calendario.get(Calendar.HOUR_OF_DAY)
                minutos=calendario.get(Calendar.MINUTE)

                todo = "$hora:$minutos"
                var tiempoPicker = TimePickerDialog(thiscontext,TimePickerDialog.OnTimeSetListener{
                        view: TimePicker?, hourOfDay: Int, minute: Int ->

                        txt_hora.setText(todo)
                },hora, minutos,false)
                tiempoPicker.show()
            }
            else ->{

            }
        }
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            calendario().apply {

            }
    }
}
