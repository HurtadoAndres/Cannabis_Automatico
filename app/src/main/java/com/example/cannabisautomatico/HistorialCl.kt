package com.example.cannabisautomatico

class HistorialCl {
    private var hora: String? = null
    private var fecha: String? = null


    constructor(hora: String?, fecha: String?) {
        this.hora = hora
        this.fecha = fecha

    }


    fun getHora(): String? {
        return hora
    }

    fun setDHora(hora: String) {
        this.hora = hora
    }

    fun getFecha(): String? {
        return fecha
    }

    fun setFecha(fecha: String) {
        this.fecha = fecha
    }

}