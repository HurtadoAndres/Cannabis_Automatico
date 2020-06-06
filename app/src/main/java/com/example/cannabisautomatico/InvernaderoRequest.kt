package com.example.cannabisautomatico

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class InvernaderoRequest: StringRequest {

    private var params: Map<String, String> = HashMap()

    constructor(email:String, titulo:String, titulo_s:String, descripcion:String, imagen_nombre:String,listener: Response.Listener<String>) :
            super(Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/invernaderoCrear.php", listener, null) {

        (params as HashMap<String, String>)["email"] = email
        (params as HashMap<String, String>)["titulo"] = titulo
        (params as HashMap<String, String>)["titulo_s"] = titulo_s
        (params as HashMap<String, String>)["descripcion"] = descripcion
        (params as HashMap<String, String>)["imagen"] = imagen_nombre


    }

    public override fun getParams(): Map<String, String> {

        return params
    }



}