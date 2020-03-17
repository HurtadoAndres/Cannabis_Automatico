package com.example.cannabisautomatico

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.hp.bluetoothjhr.BluetoothJhr
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_info__sensor.*


class Info_SensorActivity: AppCompatActivity(){


        private lateinit var mDatabase : FirebaseDatabase
        private lateinit var myRef: DatabaseReference
        var myFragmentC:Boolean=true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__sensor)



        btn_home.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }


        val fr4 = F_principal()
        fr4.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.fragm,fr4).commit()

        fab3.setOnClickListener { v ->
            Snackbar.make(v,"Modo manual consola", Snackbar.LENGTH_LONG)
                .setAction("Action",null).show()

            grupofab.collapse()

            val fr3= F_segundario()
            val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
            trantition1.replace(R.id.fragm, fr3)
            trantition1.commit()

        }

        fab4.setOnClickListener { v ->
            Snackbar.make(v,"Modo Automatico consola", Snackbar.LENGTH_LONG)
                .setAction("Action",null).show()

            grupofab.collapse()

            val fr4 = F_principal()
            val trantition1 : FragmentTransaction = supportFragmentManager.beginTransaction()
            trantition1.replace(R.id.fragm, fr4)
            trantition1.commit()

        }





        //CODIGO DE GRAFICACION EN ONCREATE---------------------------------------------------------

        var mpLineChart= findViewById<LineChart>(R.id.tabla_grafica)
        var LlineDataSet1 = LineDataSet(dataValues1(),"Data set 1")
        var LlineDataSet2 = LineDataSet(dataValues2(),"Data set 2")
        var datasets : ArrayList<LineDataSet> = ArrayList()
        datasets.add(LlineDataSet1)
        datasets.add(LlineDataSet2)


        mpLineChart.setNoDataText("No hay datos")
        mpLineChart.setNoDataTextColor(Color.BLUE)

        mpLineChart.setDrawGridBackground(true)
        mpLineChart.setDrawBorders(true)

        //cambiar de color Data set 1 o 2
        var legend = mpLineChart.legend
        legend.isEnabled=true
        legend.textColor=Color.RED
        legend.textSize=20f

        //modifica lo de alado de Data set 1 o 2
        legend.setForm(legend.form.let { Legend.LegendForm.CIRCLE}) // forma
        legend.formSize=20f //tamaño
        legend.xEntrySpace=5f //espacio
        legend.formToTextSpace=15F// espacio con el de a lado
        //---------------------------------

        //Descripcion
        val descripcion : Description = Description()
        descripcion.text="Prueba"
        descripcion.textColor=Color.BLUE
        descripcion.textSize= 20F
        mpLineChart.description=descripcion
        //----------------------------------------

        //LINEA 1

        LlineDataSet1.lineWidth=4f
        LlineDataSet1.setColor(Color.BLACK)
        LlineDataSet1.setDrawCircles(true) //circulo que aparece encada quiebre de la linea graficada
        LlineDataSet1.setDrawCircleHole(true)
        LlineDataSet1.setCircleColor(Color.GRAY) // color del circulo de cada quiebre de la linea
        LlineDataSet1.circleHoleColor=Color.GREEN //color del circulo dentro del circulo de la linea de grafica
        LlineDataSet1.circleRadius=7f // radio
        LlineDataSet1.circleHoleRadius=5f // radio del circulo dentro del circulo principal
        LlineDataSet1.valueTextSize=7f
        LlineDataSet1.valueTextColor=Color.BLACK
        LlineDataSet1.setDrawHorizontalHighlightIndicator(true)
        LlineDataSet1.setDrawFilled(true)
        LlineDataSet1.fillColor=Color.BLACK
        //LlineDataSet1.setDrawValues(true)
       // LlineDataSet1.setColor(Color.GREEN,60)

        //LINEA 2
        LlineDataSet2.lineWidth=4f
        LlineDataSet2.setColor(Color.BLUE)
        LlineDataSet2.setDrawCircles(true) //circulo que aparece encada quiebre de la linea graficada
        LlineDataSet2.setDrawCircleHole(true)
        LlineDataSet2.setCircleColor(Color.GRAY) // color del circulo de cada quiebre de la linea
        LlineDataSet2.circleHoleColor=Color.BLACK //color del circulo dentro del circulo de la linea de grafica
        LlineDataSet2.circleRadius=7f // radio
        LlineDataSet2.circleHoleRadius=5f // radio del circulo dentro del circulo principal
        LlineDataSet2.valueTextSize=7f
        LlineDataSet2.valueTextColor=Color.BLACK
        LlineDataSet2.setDrawFilled(true)

        //LlineDataSet1.enableDashedLine(5f,10f,0f)// hacer linea de puntitos
        /*
        var myArray:ArrayList<Int> =ArrayList()
        myArray.add(R.color.color1)
        myArray.add(R.color.color2)
        myArray.add(R.color.color3)
        myArray.add(R.color.color4)
        LlineDataSet1.colors = myArray

         */


        var data:LineData =  LineData(datasets as List<ILineDataSet>?)

        //metodo para poner el metodo ponerTemp
         data.setValueFormatter(ponerTemp())




        //-------------------------------------
        mpLineChart.setData(data)
        mpLineChart.invalidate()


        // -----------------------------------------------------------------------------------------

        //TRAEMOS LOS DATOS DE FIREBASE EN ESTE CASO COMO PRUEBA TRAJE ANDRES Y HURTADO
        mDatabase = FirebaseDatabase.getInstance()
        myRef = mDatabase.getReference("User")
        var myRef2 = myRef.child("6qMLWGHkNuck59ks6c5ABV8AfEG2")
        var myRef3 = myRef2.child("Name")
        myRef3.addValueEventListener(object:ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {


                    var Nombre:String = p0.getValue().toString()
                    //txtprueba.text = Nombre

            }

        })

        var myRef4 = myRef.child("6qMLWGHkNuck59ks6c5ABV8AfEG2")
        var myRef5 = myRef4.child("lastName")
        myRef5.addValueEventListener(object:ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {


                var Nombre:String = p0.getValue().toString()
                //txtprueba2.text = Nombre

            }

        })


    }




    private fun  dataValues1(): ArrayList<Entry> {
        var dataVals: ArrayList<Entry> = ArrayList<Entry>()

            dataVals.add(Entry(0F, 56F))
            dataVals.add(Entry(1F, 55F))
            dataVals.add(Entry(2F, 57F))
            dataVals.add(Entry(3F, 57F))
            dataVals.add(Entry(4F, 56F))

        return dataVals
    }

    private fun  dataValues2(): ArrayList<Entry> {
        var dataVals: ArrayList<Entry> = ArrayList<Entry>()

        dataVals.add(Entry(0F, 56F))
        dataVals.add(Entry(1F, 56F))
        dataVals.add(Entry(2F, 56F))
        dataVals.add(Entry(3F, 56F))
        dataVals.add(Entry(4F, 56F))

        return dataVals
    }

    private open class ponerTemp: ValueFormatter() {
        override fun getFormattedValue(
            value: Float,
            entry: Entry?,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler?
        ): String {
            return "$value $"
        }

    }





}
