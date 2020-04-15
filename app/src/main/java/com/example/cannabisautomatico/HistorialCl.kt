package com.example.cannabisautomatico

class HistorialCl {
    private var id: String? = null
    private var hora: String? = null
    private var fecha: String? = null


    constructor(id: String?, hora: String?, fecha: String?) {
        this.id = id
        this.hora = hora
        this.fecha = fecha

    }


    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
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