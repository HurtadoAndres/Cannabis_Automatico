package com.example.cannabisautomatico

class entidad_calendario {
    var invernadero : String = ""
    var etapa : String = ""
    var dias_etapa : Int


    constructor(
        invernadero: String,
        etapa: String,
        dias_etapa: Int

    ) {
        this.invernadero = invernadero
        this.etapa = etapa
        this.dias_etapa = dias_etapa

    }

    fun etapa():String{
        return etapa
    }

    fun invernadero():String{
        return invernadero
    }

    fun dias_etapa():Int{
        return dias_etapa
    }


}