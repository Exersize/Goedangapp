package com.example.goedangapp.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentRecentAddBinding
import com.example.goedangapp.util.ResultState

class RecentAdd : Fragment() {
    private var _binding: FragmentRecentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RecentAddViewModel>() {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentAddBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RecentAdd.viewModel
        }

        val itemEntryAdapter = ItemEntryAdapter(requireContext(), emptyList(), viewModel)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemEntryAdapter
        }

        viewModel.itemEntries.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    // Show loading state
                }
                is ResultState.Success -> {
                    itemEntryAdapter.updateItems(result.data)
                }
                is ResultState.Error -> {
                    // Show error message
                }
                else -> {}
            }
        })

        return binding.root
    }

}