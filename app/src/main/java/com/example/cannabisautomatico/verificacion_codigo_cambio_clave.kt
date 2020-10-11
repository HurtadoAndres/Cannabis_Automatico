package com.example.cannabisautomatico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import java.util.*

class verificacion_codigo_cambio_clave : AppCompatActivity() {

    var random = Random()
    lateinit var btn_enviar : Button
    var email_C : String = ""
    lateinit var  btn_confirmar: Button
    lateinit var btn_atras : LinearLayout

    lateinit var num1 : EditText
    lateinit var num2: EditText
    lateinit var num3 : EditText
    lateinit var num4 : EditText



    var numero_F : String = ""
    var n1 : String = ""
    var n2 : String = ""
    var n3 : String = ""
    var n4 : String = ""

    var numero : Int = 0

    lateinit var  cargando :LottieAnimationView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificacion_codigo_cambio_clave)
        btn_enviar = findViewById(R.id.btn_enviarr)
        btn_confirmar = findViewById(R.id.btn_confirmar)
        cargando = findViewById(R.id.cargando)
        btn_atras = findViewById(R.id.btn_atras)

        num1 = findViewById(R.id.numero1)
        num2 = findViewById(R.id.numero2)
        num3 = findViewById(R.id.numero3)
        num4 = findViewById(R.id.numero4)

        btn_enviar = findViewById(R.id.btn_enviarr)


       numero = intent?.getIntExtra("codigo", 0)!!
       var pagina = intent?.getIntExtra("pagina", 0)
       var email_enviado = intent?.getStringExtra("email")

        if (pagina == 1) {
            email_C = obtenerEmail().toString()
        }else{
            email_C = email_enviado.toString()
        }


        var hilo = Hilo(numero, email_C)
          hilo.start()

        btn_enviar.setOnClickListener {
            numero = (1000 + random.nextInt((9999) - 1000))
            var hilos = Hilo(numero, email_C)
            hilos.start()
            Toast.makeText(this,"Hemos enviado el codigo a tu correo",Toast.LENGTH_LONG).show()
        }

        btn_atras.setOnClickListener {
            if (pagina == 1){
                val intent =Intent(this, HomeActivity::class.java)
                intent.putExtra("pagina_fr", 4)
                startActivity(intent)
            }else{
                startActivity(Intent(this, Olvide_passwordActivity::class.java))
            }
        }



        btn_confirmar.setOnClickListener {
            n1 = num1.text.toString()
            n2 = num2.text.toString()
            n3 = num3.text.toString()
            n4 = num4.text.toString()
            numero_F = "$n1$n2$n3$n4"
            cargando.visibility = (View.VISIBLE)

            if (numero_F == numero.toString()){
                cargando.visibility = (View.INVISIBLE)
                var intent = Intent(this,Cambio_clave::class.java)
                intent.putExtra("pagina",pagina)
                startActivity(intent)
            }else{
                cargando.visibility = (View.INVISIBLE)
                Toast.makeText(this,"El codigo es incorreto", Toast.LENGTH_LONG).show()
            }
        }
    }

    class Hilo : Thread {
        var numero : Int = 0
        var email_c : String = ""

        constructor(numero: Int, email_c: String) : super() {
            this.numero = numero
            this.email_c = email_c
        }

        override fun run() {
            super.run()
            Enviar_correo(email_c,numero)
        }
    }

    fun obtenerEmail(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }


}