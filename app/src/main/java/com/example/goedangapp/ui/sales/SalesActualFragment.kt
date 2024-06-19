package com.example.goedangapp.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.goedangapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [SalesActualFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SalesActualFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_actual, container, false)
    }

}