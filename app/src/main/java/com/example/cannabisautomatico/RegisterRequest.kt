package com.example.cannabisautomatico

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class RegisterRequest: StringRequest {

     private var params: Map<String, String> = HashMap()

    constructor(name:String, ape:String, usu:String, pass:String, listener: Response.Listener<String>) :
            super(Method.POST, "https://cannabisautomatico.000webhostapp.com/ServicioWeb/Registrar.php", listener, null) {

        (params as HashMap<String, String>)["nombre"] = name
        (params as HashMap<String, String>)["apellido"] = ape
        (params as HashMap<String, String>)["email"] = usu
        (params as HashMap<String, String>)["password"] = pass


    }

    public override fun getParams(): Map<String, String> {

        return params
    }



}