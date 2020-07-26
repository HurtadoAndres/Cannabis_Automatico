package com.example.cannabisautomatico

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class RegisterValidaRequest: StringRequest {
    private var params: Map<String, String> = HashMap()

    constructor(usuario:String, codigo:String, listener: Response.Listener<String>) :
            super(Request.Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/validarCod.php", listener, null) {

        (params as HashMap<String, String>)["email"] = usuario
        (params as HashMap<String, String>)["codigo"] = codigo


    }

    public override fun getParams(): Map<String, String> {

        return params
    }
}