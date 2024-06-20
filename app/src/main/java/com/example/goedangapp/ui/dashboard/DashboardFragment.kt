package com.example.goedangapp.ui.dashboard

import DashboardViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentDashboardBinding
import com.example.goedangapp.util.ResultState


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.username.text = user.name
        }
        viewModel.getLowInStock().observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is ResultState.Loading -> {
                    // Show loading indicator
                }
                is ResultState.Success -> {
                    val filteredAndSortedItems = result.data
                    val firstItem = filteredAndSortedItems.firstOrNull()
                    val lowStockQtyText : String


                    firstItem?.let {
                        val lowStockQty = it.quantity
                        val lowStockMeasuringUnit = it.measuringUnit
                        lowStockQtyText = "$lowStockQty $lowStockMeasuringUnit"

                        binding.lowestInStock.visibility = View.VISIBLE
                        binding.lowestInStockItem.text = it.name

                        binding.lowestInStockQty.text = lowStockQtyText
                    } ?: run {
                        binding.lowestInStock.visibility = View.GONE
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        viewModel.getSortedItemEntries().observe(viewLifecycleOwner, Observer { result ->
//            when (result) {
//                is ResultState.Loading -> {}
//                is ResultState.Success -> {
//                    val sortedItem = result.data
//                    val firstItem = sortedItem.firstOrNull()
//
//                    firstItem?.let {
//
////                        val lowStockQty = it.quantity
////                        val lowStockMeasuringUnit = it.measuringUnit
////                        recentlyAddText = "$lowStockQty $lowStockMeasuringUnit"
////
////                        binding.lowestInStock.visibility = View.VISIBLE
////                        binding.lowestInStockItem.text = it.name
////
////                        binding.lowestInStockQty.text = lowStockQtyText
//                    } ?: run {
//                        binding.lowestInStock.visibility = View.GONE
//                    }
//                }
//            }
//        })
    }
}