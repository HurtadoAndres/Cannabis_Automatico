package com.example.cannabisautomatico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListaAdapter: ArrayAdapter<HistorialCl>{

     var mlist: List<HistorialCl>
     var mcontext : Context
     var resorcelayout : Int

    constructor(context: Context, resource: Int, objects: ArrayList<HistorialCl>) :   super(
        context,
        resource,
        objects
    ){

        this.mlist=objects
        this.mcontext=context
        this.resorcelayout = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view : View? = convertView

        if (view == null)
            view = LayoutInflater.from(mcontext).inflate(resorcelayout, null)

        var modelo : HistorialCl = mlist.get(position)
        var id = view?.findViewById<TextView>(R.id.id)
        if (id != null) {
            id.text=modelo.getId()
        }
        var hora= view?.findViewById<TextView>(R.id.hora)
        if (hora != null) {
            hora.text=modelo.getHora()
        }
        var fecha = view?.findViewById<TextView>(R.id.fecha)
        if (fecha != null) {
            fecha.text=modelo.getFecha()
        }



        return view!!

    }
}