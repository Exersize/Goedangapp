package com.example.goedangapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.goedangapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private val placeholder: List<String> = listOf("Item 1", "Item 2", "Item 3", "Item 4")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupItemAdapter()
        return binding.root
    }

    private fun setupItemAdapter() {
        adapterItems = ArrayAdapter<String>(requireContext(), R.layout.list_item, placeholder)
        binding.autoCompleteText.setAdapter(adapterItems)
        binding.autoCompleteText.setOnItemClickListener { adapterView, view, i, l ->
            val items = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireContext(), "Selected item: $items", Toast.LENGTH_SHORT).show()
        }
    }

}