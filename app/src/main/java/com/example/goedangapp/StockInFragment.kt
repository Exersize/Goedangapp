package com.example.goedangapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.goedangapp.databinding.FragmentStockInBinding


class StockInFragment : Fragment() {

    private var _binding : FragmentStockInBinding? = null
    private val binding get() = _binding!!
    private var placeholder : List<String> = listOf("Cabe", "Bawang", "Kacang")
    private lateinit var  autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockInBinding.inflate(inflater, container, false)
        setupItemAdapter()
        return binding.root
    }

    private fun setupItemAdapter() {
        adapterItems = ArrayAdapter<String>(requireContext(), R.layout.list_item, placeholder)
        binding.autoCompleteText.setAdapter(adapterItems)
        binding.autoCompleteText.setOnItemClickListener{ adapterView, view, i, l ->
            val items = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireContext(), "Selected item: $items", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}