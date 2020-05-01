package com.example.cannabisautomatico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class calendario : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendario, container, false)
        var texto = view.findViewById<TextView>(R.id.texto)
        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            calendario().apply {

            }
    }
}
