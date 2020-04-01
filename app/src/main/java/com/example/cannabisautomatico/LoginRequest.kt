package com.example.cannabisautomatico

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class LoginRequest: StringRequest {

    private var params: Map<String, String> = HashMap()

    constructor(usu:String, pass:String, listener: Response.Listener<String>) :
            super(Request.Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/login.php", listener, null) {

        (params as HashMap<String, String>)["username"] = usu
        (params as HashMap<String, String>)["password"] = pass


    }

    public override fun getParams(): Map<String, String> {

        return params
    }
}