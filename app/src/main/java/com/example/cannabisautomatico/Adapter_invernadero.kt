package com.example.cannabisautomatico


import android.content.Context
import android.text.BoringLayout
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import lecho.lib.hellocharts.model.Line


class Adapter_invernadero: BaseExpandableListAdapter {

    private var context : Context
    private var listitulo : List<String> = arrayListOf()
    private  var expandableListDetalle : Map<String, Invernadero> = HashMap()
    private var seleccionado : Boolean = false


    constructor(
        context: Context,
        listitulo: List<String>,
        expandableListDetalle: Map<String, Invernadero>
    ) : super() {
        this.context = context
        this.listitulo = listitulo
        this.expandableListDetalle = expandableListDetalle
    }

    constructor(context: Context, seleccionado: Boolean) : super() {
        this.context = context
        this.seleccionado = seleccionado
    }


    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view : View? = convertView
        val invernadero = getChild(groupPosition,childPosition)

        if (convertView == null){
            val layoutinflater : LayoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = layoutinflater.inflate(R.layout.list_informacion,null)

        }

        val circleimagen : CircleImageView?
        val titulo_planta : TextView?
        val titulo_planta_des : TextView?

        circleimagen = view?.findViewById(R.id.profile_image)
        titulo_planta  = view?.findViewById(R.id.titulo_planta)
        titulo_planta_des = view?.findViewById(R.id.titulo_planta_descrip)

        if (circleimagen != null) {
            Glide.with(context).load(invernadero?.getImg()).into(circleimagen)
        }
        titulo_planta?.text = invernadero?.getTitulo_s()
        titulo_planta_des?.text = invernadero?.getDescripcion()


        val animacion : Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        convertView?.startAnimation(animacion)


        return view
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view: View? = convertView
        var txttitulo: TextView
        var invernadero: Invernadero? = getGroup(groupPosition)


        if (convertView == null) {
            var layoutinflater: LayoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = layoutinflater.inflate(R.layout.item_list_invernadero, null)
        }

        txttitulo = view?.findViewById(R.id.titulo)!!
        txttitulo.text = invernadero?.getTitulo()
        var sele: LinearLayout
        sele = view.findViewById(R.id.seleccionado)

        if (isExpanded) {
            txttitulo.setTextColor(view.resources.getColor(R.color.cannabis_verde_D))
            //txttitulo.textSize = 20f
        } else {
            txttitulo.setTextColor(view.resources.getColor(R.color.cannabis_color_negro))
            // txttitulo.textSize = 18f
        }

         sele = view.findViewById(R.id.seleccionado)
        var b : Boolean = seleccionado
        Toast.makeText(context,""+b,Toast.LENGTH_LONG).show()

        if (seleccionado){
            sele.visibility = (View.VISIBLE)
        }else{
            sele.visibility = (View.INVISIBLE)
        }

        return view
    }



    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Invernadero? {
        return this.expandableListDetalle[this.listitulo[groupPosition]]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }


    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }


    override fun getGroup(groupPosition: Int): Invernadero? {
        return this.expandableListDetalle[this.listitulo[groupPosition]];
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }


    override fun getGroupCount(): Int {
        return listitulo.size
    }





}


