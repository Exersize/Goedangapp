package com.example.goedangapp.ui.sales

import SalesAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentSalesBinding
import com.example.goedangapp.repository.CustomData
import com.example.goedangapp.response.LastEntry
import com.example.goedangapp.ui.MainActivity
import com.example.goedangapp.util.OnItemClickListener
import com.example.goedangapp.util.ResultState

class SalesFragment : Fragment() {
    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SalesAdapter
    private val items = mutableListOf<CustomData>()
    private val viewModel: FragmentSalesViewModel by viewModels<FragmentSalesViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SalesAdapter(requireContext(), items, object : OnItemClickListener{
            override fun onItemClick(customData: CustomData) {
                val intent = Intent(requireContext(), SalesDetailActivity::class.java)
                // Pass any necessary data to the activity
                intent.putExtra("itemId", customData.id)
                requireContext().startActivity(intent)
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.itemLastEntries.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    // Show loading indicator
                }
                is ResultState.Success -> {
                    val items = result.data
                    adapter.updateItems(items)
                }
                is ResultState.Error -> {
                    // Handle the error state
                    Log.e("SalesFragment", "Error fetching items: ${result.error}")
                }
                else -> {}
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}