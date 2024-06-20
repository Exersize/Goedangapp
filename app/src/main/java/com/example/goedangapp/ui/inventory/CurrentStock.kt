package com.example.goedangapp.ui.inventory

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.adapter.ItemAdapter
import com.example.goedangapp.databinding.FragmentCurrentStockBinding
import com.example.goedangapp.util.ResultState

class CurrentStock : Fragment() {
    private var _binding: FragmentCurrentStockBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CurrentStockViewModel>(){
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentStockBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CurrentStock.viewModel
        }

        val adapter = ItemAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    // Show loading state
                }
                is ResultState.Success -> {
                    adapter.submitList(result.data)
                }
                is ResultState.Error -> {
                    // Show error message
                }
                else ->{}
            }
        })

        return binding.root
    }
}