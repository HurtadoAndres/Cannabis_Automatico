package com.example.cannabisautomatico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Mantenimiento_fr : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mantenimiento_fr, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            Mantenimiento_fr().apply {

            }
    }
}
