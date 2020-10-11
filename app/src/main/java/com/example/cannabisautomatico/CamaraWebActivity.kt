package com.example.cannabisautomatico

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_camara_web.*

class CamaraWebActivity : AppCompatActivity() {
    lateinit var btn_atras : LinearLayout
    var url : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara_web)
        btn_atras = findViewById(R.id.btn_atras)

        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("ip").toString()
        }

        btn_atras.setOnClickListener {
            finish()
        }

        webview.webChromeClient = object  : WebChromeClient(){

        }

        webview.webViewClient = object  : WebViewClient(){

        }

        val setting: WebSettings = webview.settings
        setting.javaScriptEnabled=true
        webview.settings.loadWithOverviewMode = true;
        webview.settings.useWideViewPort = true;
        webview.loadUrl("http://$url")

    }

    fun ipObtener(): String? {
        var preferenfs= getSharedPreferences("archivo_usu", Context.MODE_PRIVATE)
        return  preferenfs?.getString("email","")
    }
}