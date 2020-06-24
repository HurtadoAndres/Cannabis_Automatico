package com.example.cannabisautomatico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class cuenta_fr : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view :View = inflater.inflate(R.layout.fragment_cuenta_fr, container, false)



        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cuenta_fr().apply {

            }
    }
}