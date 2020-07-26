package com.example.cannabisautomatico

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class add_invernadero_Activity : AppCompatActivity() {


    lateinit var crear_invernadero: Button
    lateinit var titulo: TextView
    lateinit var titulo_s: TextView
    lateinit var descripcion: TextView

    lateinit var btn_buscar: LinearLayout
    lateinit var btn_atras: LinearLayout
    lateinit var imagen_subir: ImageView
    var bitmap: Bitmap?=null


    var KEY_IMAGE = "imagen"
    var KEY_TITULO = "titulo"
    var KEY_IMAGE_D = "imagen"
    var KEY_TITULO_S = "titulo_s"
    var KEY_DESCRIPCION = "descripcion"
    var KEY_EMAIL= "email"
    var KEY_CODIGO = "codigo"

    var UPLOAD_URL = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/subir_imagen.php"
    var UPLOAD_URL2 = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/invernaderoCrear.php"


    var carpetaRAIZ: String = "Cannabis_Automatico/"
    var rutaIMAGEN: String = carpetaRAIZ + "MyCannabis"
    var path: String = ""
    var COD_SELECCIONA: Int = 10
    var COD_FOTO: Int = 11

    lateinit var etapa1 :LinearLayout
    lateinit var etapa2 :LinearLayout
    lateinit var etapa3 :LinearLayout
    lateinit var etapa4 :LinearLayout

    var seleccionado_etapa1 : Boolean = false
    var seleccionado_etapa2 : Boolean = false
    var seleccionado_etapa3 : Boolean = false
    var seleccionado_etapa4 : Boolean = false




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_invernadero_)
        crear_invernadero = findViewById(R.id.crear_invernadero)
        titulo = findViewById(R.id.titulo)
        titulo_s = findViewById(R.id.titulo_s)
        descripcion = findViewById(R.id.descripcion)
        btn_buscar = findViewById(R.id.btn_buscar)
        imagen_subir = findViewById(R.id.imagen_subir)
        btn_atras = findViewById(R.id.btn_atras)
        etapa1 = findViewById(R.id.select_etapa1)
        etapa2 = findViewById(R.id.select_etapa2)
        etapa3 = findViewById(R.id.select_etapa3)
        etapa4 = findViewById(R.id.select_etapa4)


        btn_buscar.setOnClickListener {
        seleccionarImagen()

        }

        btn_atras.setOnClickListener {
            finish()
        }

        crear_invernadero.setOnClickListener {

            insertDatosInfromacion()

        }

        etapa1.setOnClickListener {
            seleccionado_etapa1 = !seleccionado_etapa1
            if (seleccionado_etapa1) {
                etapa1.background = getDrawable(R.drawable.btn_select_etapa_s)
            }else{
                etapa1.background = getDrawable(R.drawable.btn_select_etapa)
            }
        }


        etapa2.setOnClickListener {
            seleccionado_etapa2 = !seleccionado_etapa2
            if (seleccionado_etapa2) {
                etapa2.background = getDrawable(R.drawable.btn_select_etapa2_s)
            }else{
                etapa2.background = getDrawable(R.drawable.btn_select_etapa2)
            }
        }

        etapa3.setOnClickListener {
            seleccionado_etapa3 = !seleccionado_etapa3
            if (seleccionado_etapa3) {
                etapa3.background = getDrawable(R.drawable.btn_select_etapa3_s)
            }else{
                etapa3.background = getDrawable(R.drawable.btn_select_etapa3)
            }
        }

        etapa4.setOnClickListener {
            seleccionado_etapa4 = !seleccionado_etapa4
            if (seleccionado_etapa4) {
                etapa4.background = getDrawable(R.drawable.btn_select_etapa4_s)
            }else{
                etapa4.background = getDrawable(R.drawable.btn_select_etapa4)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun permisoCAMARA()
    {
        val REQUEST_CODE_ASK_PERMISSIONS = 111

        var permisoCAMARA :Int = ActivityCompat.checkSelfPermission (this, Manifest.permission.CAMERA);
        var permisoALMACENAMIENTO :Int = ActivityCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permisoALMACENAMIENTO != PackageManager.PERMISSION_GRANTED || permisoCAMARA != PackageManager.PERMISSION_GRANTED)
        {
            if ( Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){

            }
            requestPermissions( arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA  ),
                REQUEST_CODE_ASK_PERMISSIONS );

        }



    }


    //COnvertir imagen a String para poder subirla  a la BD
    fun convertirImagenString(bmp : Bitmap?):String{
        val baos = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG,100,baos)
        var imagenbytes: ByteArray = baos.toByteArray()
        var encodeImagen : String = Base64.encodeToString(imagenbytes,Base64.DEFAULT)

        return encodeImagen
    }


    fun tomarFoto() {
        var nombreImagen = ""
        val fileImagen =
            File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), rutaIMAGEN)
        var isCreada = fileImagen.exists()
        if (!isCreada) {
            isCreada = fileImagen.mkdirs()
        }
        if (isCreada) {
            nombreImagen = (System.currentTimeMillis() / 1000).toString() + ".jpg"
        }
        path = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator + rutaIMAGEN + File.separator + nombreImagen
        val imagen = File(path)
        var intent: Intent? = null
        intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authorities = this.packageName + ".provider"
            val imageUri = FileProvider.getUriForFile(this, authorities, imagen)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))
        }
        startActivityForResult(intent, COD_FOTO)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun seleccionarImagen() {

        permisoCAMARA()

        var opciones: Array<CharSequence> = arrayOf("Tomar foto", "Cargar imagen", "Cancelar")
        var alertOpciones: AlertDialog.Builder = AlertDialog.Builder(this)
        alertOpciones.setTitle("Seleccione una Opción")
        alertOpciones.setItems(opciones) { dialog, which ->
            if (opciones[which] == "Tomar foto"){

                tomarFoto()

            }else{
                if (opciones[which] == "Cargar imagen"){
                    var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"),COD_SELECCIONA)
                }else{
                    dialog.dismiss()
                }
            }
        }
        alertOpciones.show()



    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
          try {

            when(requestCode) {
                COD_SELECCIONA->
                 {
                        var filePath : Uri? = data?.data!!
                        //como tener el mapa de bitts de la galeria
                        bitmap =when {
                            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                filePath
                            )
                            else -> {
                                val source = ImageDecoder.createSource(this.contentResolver, filePath!!)
                                ImageDecoder.decodeBitmap(source)
                            }
                        }
                        //configurar la imagen en el ImageView
                        imagen_subir.setImageBitmap(bitmap)


                  }
                COD_FOTO ->
                  {
                      Toast.makeText(this,"aqui",Toast.LENGTH_LONG).show()
                        MediaScannerConnection.scanFile(this, arrayOf(path),null
                        ) { path:String, uri:Uri ->
                            Log.i("Ruta de almacenamiento", "Path: $path")

                        }

                      var bitmap :Bitmap = BitmapFactory.decodeFile(path)
                        imagen_subir.setImageBitmap(bitmap)


                  }
            }




            }catch (e:Exception){e.printStackTrace() }
        }

        try {
            bitmap = redimencionarBitmap(bitmap!!, 600F, 800F)
        }catch (e:java.lang.Exception){e.printStackTrace()}

    }

    fun redimencionarBitmap(bitmap:Bitmap, an: Float, al:Float):Bitmap{

        var ancho = bitmap.width
        var alto = bitmap.height
        if (ancho > an || alto > al){
            var escalaAncho : Float = an/ancho
            var escalaAlto : Float = al/alto

            val matrix = Matrix()
            matrix.postScale(escalaAncho,escalaAlto)
            return  Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false)
        }else{
            return bitmap
        }


    }


    fun uploadImage(response: String) {
        var codigo = response
        val loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, UPLOAD_URL,
            Response.Listener { response ->
                loading.dismiss()
                startActivity(Intent(this,HomeActivity::class.java))

            }, Response.ErrorListener { error ->
                loading.dismiss()
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var imagen: String = convertirImagenString(bitmap)
                var titulo :String = titulo.text.toString().trim()
                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_IMAGE] = imagen
                params[KEY_TITULO] = titulo
                params[KEY_CODIGO] =  codigo


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun insertDatosInfromacion(){
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, UPLOAD_URL2,
            Response.Listener { response ->
                uploadImage(response)
                Toast.makeText(this, "$response", Toast.LENGTH_LONG)
                    .show()
            }, Response.ErrorListener { error ->

                Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
              var titulo: String = titulo.text.toString().trim()
                var imagen_nombre :String = "imagenes/$titulo.png"
                var titulo_s :String = titulo_s.text.toString().trim()
                var descripcion :String = descripcion.text.toString().trim()
                var email:String = obtenerEmail().toString()
                //var email:String = "hurtadoandres942@gmail.com"

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_EMAIL] = email
                params[KEY_TITULO] = titulo
                params[KEY_TITULO_S] = titulo_s
                params[KEY_DESCRIPCION] = descripcion
                params[KEY_IMAGE_D] = imagen_nombre


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun obtenerEmail(): String? {
        var preferenfs= this.getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }


}








