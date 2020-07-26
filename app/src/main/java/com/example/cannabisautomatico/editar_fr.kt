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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class editar_fr : Fragment() {
    var titu_e: String? = null
    var titu_s_e: String? = null
    var des_e: String? = null
    var id_e: String? = null
    var ruta_e: String? = null

    lateinit var txttitulo : EditText
    lateinit var txttitulo_s : EditText
    lateinit var txtdescripcion : EditText
    lateinit var imagen_mostrar : CircleImageView
    lateinit var thiscontext :Context

    var update : String = "false"

    var KEY_CODIGO = "codigo"
    var KEY_IMAGE = "imagen"
    var KEY_TITULO = "titulo"
    var KEY_TITULO_S = "titulo_s"
    var KEY_DES = "descripcion"
    var URL_EDITAR ="https://cannabisautomatico.000webhostapp.com/ServicioWeb/actualizarInvernadero.php"
    var UPLOAD_URL = "https://cannabisautomatico.000webhostapp.com/ServicioWeb/subir_imagen.php"

    var carpetaRAIZ: String = "Cannabis_Automatico/"
    var rutaIMAGEN: String = carpetaRAIZ + "MyCannabis"
    var path: String = ""
    var COD_SELECCIONA: Int = 10
    var COD_FOTO: Int = 11
    lateinit var bitmap: Bitmap

    lateinit var btn_confirmar : Button
    lateinit var btn_subir_imagen :LinearLayout
    var click_imagen :Boolean = false






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
           titu_e = arguments?.getString("titulo")
           titu_s_e = arguments?.getString("titulo_s")
           des_e = arguments?.getString("descripcion")
           id_e = arguments?.getString("id")
           ruta_e = arguments?.getString("ruta_img")

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_editar_fr, container, false)
        txttitulo = view.findViewById(R.id.titulo)
        txttitulo_s = view.findViewById(R.id.titulo_s)
        txtdescripcion = view.findViewById(R.id.descripcion)
        imagen_mostrar = view.findViewById(R.id.profile_image)
        btn_confirmar = view.findViewById(R.id.btn_confirmar)
        btn_subir_imagen = view.findViewById(R.id.btn_buscar)

        txttitulo.setText(titu_e)
        txttitulo_s.setText(titu_s_e)
        txtdescripcion.setText(des_e)


        if (container != null) {
            thiscontext = container.context
        }

        Glide.with(thiscontext).load(ruta_e).into(imagen_mostrar)

        btn_confirmar.setOnClickListener {
            editar()
        }

        btn_subir_imagen.setOnClickListener {
            click_imagen = true
            seleccionarImagen()
        }

        return view
    }
    fun editar() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URL_EDITAR,
            Response.Listener { response ->
                if (click_imagen){
                    update == "true"
                    uploadImage()
                }
                startActivity(Intent(thiscontext, HomeActivity::class.java))

            }, Response.ErrorListener { error ->

                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var imagen: String = update
                var titulo :String = txttitulo.text.toString().trim()
                var titulo_s :String = txttitulo_s.text.toString().trim()
                var descripcion :String = txtdescripcion.text.toString().trim()

                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_IMAGE] = imagen
                params[KEY_TITULO] = titulo
                params[KEY_TITULO_S] = titulo_s
                params[KEY_DES] = descripcion
                params[KEY_CODIGO] = id_e.toString()

                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun permisoCAMARA()
    {
        val REQUEST_CODE_ASK_PERMISSIONS = 111

        var permisoCAMARA :Int = ActivityCompat.checkSelfPermission (thiscontext, Manifest.permission.CAMERA);
        var permisoALMACENAMIENTO :Int = ActivityCompat.checkSelfPermission (thiscontext, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permisoALMACENAMIENTO != PackageManager.PERMISSION_GRANTED || permisoCAMARA != PackageManager.PERMISSION_GRANTED)
        {
            if ( Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){

            }
            requestPermissions( arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA  ),
                REQUEST_CODE_ASK_PERMISSIONS );

        }



    }


    //COnvertir imagen a String para poder subirla  a la BD
    fun convertirImagenString(bmp : Bitmap):String{
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos)
        var imagenbytes: ByteArray = baos.toByteArray()
        var encodeImagen : String = Base64.encodeToString(imagenbytes, Base64.DEFAULT)

        return encodeImagen
    }


    fun tomarFoto() {
        var nombreImagen = ""
        val fileImagen =
            File(Environment.getExternalStorageDirectory(), rutaIMAGEN)
        var isCreada = fileImagen.exists()
        if (!isCreada) {
            isCreada = fileImagen.mkdirs()
        }
        if (isCreada) {
            nombreImagen = (System.currentTimeMillis() / 1000).toString() + ".jpg"
        }
        path = Environment.getExternalStorageDirectory()
            .toString() + File.separator + rutaIMAGEN + File.separator + nombreImagen
        val imagen = File(path)
        var intent: Intent? = null
        intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authorities = thiscontext.packageName + ".provider"
            val imageUri = FileProvider.getUriForFile(thiscontext, authorities, imagen)
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
        var alertOpciones: AlertDialog.Builder = AlertDialog.Builder(thiscontext)
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
                                thiscontext.contentResolver,
                                filePath
                            )
                            else -> {
                                val source = ImageDecoder.createSource(thiscontext.contentResolver, filePath!!)
                                ImageDecoder.decodeBitmap(source)
                            }
                        }
                        //configurar la imagen en el ImageView
                        imagen_mostrar.setImageBitmap(bitmap)


                    }
                    COD_FOTO ->
                    {
                        Toast.makeText(thiscontext,"aqui",Toast.LENGTH_LONG).show()
                        MediaScannerConnection.scanFile(thiscontext, arrayOf(path),null
                        ) { path:String, uri: Uri ->
                            Log.i("Ruta de almacenamiento", "Path: $path")

                        }

                        var bitmap : Bitmap = BitmapFactory.decodeFile(path)
                        imagen_mostrar.setImageBitmap(bitmap)


                    }
                }




            }catch (e:Exception){e.printStackTrace() }
        }

        try {
            bitmap = redimencionarBitmap(bitmap, 600F, 800F)
        }catch (e:java.lang.Exception){e.printStackTrace()}

    }

    fun redimencionarBitmap(bitmap: Bitmap, an: Float, al:Float): Bitmap {

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

    fun uploadImage() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, UPLOAD_URL,
            Response.Listener { response ->
            }, Response.ErrorListener { error ->
                Toast.makeText(thiscontext, error.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var imagen: String = convertirImagenString(bitmap)
                var titulo :String =  txttitulo.text.toString().trim()
                var params: MutableMap<String, String> =
                    Hashtable<String, String>()
                params[KEY_IMAGE] = imagen
                params[KEY_TITULO] = titulo
                params[KEY_CODIGO] = id_e.toString()


                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(thiscontext)
        requestQueue.add(stringRequest)

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            editar_fr().apply {

            }
    }
}