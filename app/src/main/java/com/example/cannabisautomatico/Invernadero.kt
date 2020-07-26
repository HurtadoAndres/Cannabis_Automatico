package com.example.cannabisautomatico

class Invernadero {

    private var titulo: String? = null
    private var titulo_s: String? = null
    private var descripcion: String? = null
    private var imagen :String? = null

    constructor(titulo: String?, titulo_s: String?, descripcion: String?, imagen: String?) {
        this.titulo = titulo
        this.titulo_s = titulo_s
        this.descripcion = descripcion
        this.imagen = imagen
    }


    fun getTitulo(): String? {
        return titulo
    }

    fun setTitulo(titulo: String?) {
        this.titulo = titulo
    }

    fun getTitulo_s(): String?{
        return titulo_s
    }

    fun setTitulo_s_(titulo_s: String?) {
        this.titulo_s = titulo_s
    }

    fun getDescripcion(): String? {
        return descripcion
    }

    fun settDescripcion(descripcion: String?) {
        this.descripcion = descripcion
    }

    fun getImg(): String? {
        return imagen
    }

    fun settImg(imagen: String) {
        this.imagen = imagen
    }

}