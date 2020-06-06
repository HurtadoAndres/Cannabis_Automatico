package com.example.cannabisautomatico

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class InvernaderoDatosRequest : StringRequest {

    private var params: Map<String, String> = HashMap()

    constructor(email:String,listener: Response.Listener<String>) :
    super(Request.Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/invernaderoTraer.php", listener, null) {

        (params as HashMap<String, String>)["email"] = email


    }

    public override fun getParams(): Map<String, String> {

        return params
    }



}