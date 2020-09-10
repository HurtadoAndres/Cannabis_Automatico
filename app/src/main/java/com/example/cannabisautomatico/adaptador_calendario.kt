package com.example.cannabisautomatico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import java.util.*
import kotlin.collections.ArrayList

class adaptador_calendario: BaseAdapter {
    var context : Context
    var listitems = ArrayList<entidad_calendario>()

    constructor(context: Context, listitems: ArrayList<entidad_calendario>) : super() {
        this.context = context
        this.listitems = listitems
    }

    override fun getItem(p0: Int): Any {
        return listitems[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listitems.size
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
       var view : View
        var item = getItem(p0) as entidad_calendario
        view = LayoutInflater.from(context).inflate(R.layout.adapter_lisview,null)

        var etapa = view.findViewById<TextView>(R.id.etapa)
        var invernadero = view.findViewById<TextView>(R.id.inverdaero_list)
        var cantidad = view.findViewById<TextView>(R.id.cantidad)

        var calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        etapa.text = item.etapa()
        invernadero.text = item.invernadero()
        cantidad.text = "Dias total de la etapa " + item.dias_etapa()


        val events: MutableList<EventDay> = java.util.ArrayList()

        val calendar = Calendar.getInstance()
        calendarView.setHeaderColor(R.color.cannabis_color_gris);
        calendarView.setHeaderLabelColor(R.color.cannabis_verde_D);



        for (i in 0 until item.dias_etapa()) {
            val calendar2 = Calendar.getInstance()
            calendar2.add(Calendar.DAY_OF_MONTH, i)
            events.add(
                EventDay(
                    calendar2,
                    R.drawable.circulo
                )
            )
        }


        calendarView.setEvents(events)

        calendarView.setDate(calendar)

        val calendars: List<Calendar> = java.util.ArrayList()
        calendarView.setDisabledDays(calendars)

        calendarView.setHighlightedDays(calendars)

        calendarView.selectedDates = calendars





        return view
    }


}