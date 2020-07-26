package com.example.cannabisautomatico

import android.os.StrictMode
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class Enviar_correo {
    var correoOficial = ""
    var contraseñaOficial = ""
    lateinit var session : Session

    var email : String? = null
    var codigo : Int = 0

    constructor(email: String?, codigo: Int) {
        this.email = email
        this.codigo = codigo

        correoOficial  = "cannabisautomatico@gmail.com"
        contraseñaOficial  = "CannabisAutomaticoFup2020"
        metodoCorreo()

    }


    fun metodoCorreo(){
        var Contenido: String ="<h1>Usted recibio un restablecimiento de contraseña,</h1>"+
                "        <h2>para su cuenta $email.</h2>" + "<br>" +
                "        <p>Para confirmar esta peticion y " +
                "        restablecer una nueva contraseña,</p>" +
                "        <p>tu codigo es:<b> $codigo </b>" + "<br>" + "<br>" +
                "        <p>Por favor no responder a este correo ya que solo</p>" +
                "        <p> es de enviar mensaje y no recibir.</p>" + "<br>" +
                "        <h3><p>Cannabis Automatico App</p></h3>"

        val politica : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(politica)

        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.googlemail.com"
        properties["mail.smtp.socketFactory.port"] = "465"
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = "465"


        try {
            session = Session.getDefaultInstance(properties,object: Authenticator(){
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(correoOficial,contraseñaOficial)
                }
            })

            val mensaje: Message = MimeMessage(session)
            mensaje.setFrom(InternetAddress(correoOficial))
            mensaje.subject = "Hola "
            mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            )
            mensaje.setContent(Contenido,"text/html; charset=utf-8")
            Transport.send(mensaje)


        }catch (e:Exception){e.printStackTrace()}



    }
}