package com.example.cannabisautomatico

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class ConsultaCodvalidacion : StringRequest{
    private var params: Map<String, String> = HashMap()

    constructor(email:String, codigo:String, listener: Response.Listener<String>) :
            super(Request.Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/ConsutaValidar.php", listener, null) {

        (params as HashMap<String, String>)["email"] = email
        (params as HashMap<String, String>)["codigo"] = codigo


    }

    public override fun getParams(): Map<String, String> {

        return params
    }
}