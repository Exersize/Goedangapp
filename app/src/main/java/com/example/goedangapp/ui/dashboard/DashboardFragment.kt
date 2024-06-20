package com.example.goedangapp.ui.dashboard

import DashboardViewModel
import android.os.Bundle
import android.util.Log
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
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


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

        viewModel.getSortedItemEntries().observe(viewLifecycleOwner, Observer { result ->
            when (result) {

                is ResultState.Loading -> {}
                is ResultState.Success -> {
                    val sortedItem = result.data
                    val firstItem = sortedItem.firstOrNull()
                    var recentlyAddText : String

                    firstItem?.let {
                        viewModel.getItemById(it.itemId ?: "").observe(viewLifecycleOwner, Observer { itemDetailResult ->
                            when (itemDetailResult) {
                                is ResultState.Success -> {
                                    val itemDetail = itemDetailResult.data
                                    val recentlyAddQty = it.quantity
                                    val recentlyAddMeasuringUnit = itemDetail.measuringUnit
                                    val recentlyAddName = itemDetail.name
                                    val recentlyAddDate = formatDateString(it.createdAt)

                                    recentlyAddText = "$recentlyAddQty $recentlyAddMeasuringUnit"

                                    binding.recentlyAdd.visibility = View.VISIBLE
                                    binding.recentlyAddItem.text = recentlyAddName
                                    binding.recentlyAddQty.text = recentlyAddText
                                    binding.recentlyAddDate.text = recentlyAddDate

                                }
                                is ResultState.Error -> {
                                    Log.e("DashboardFragment", "Error fetching item detail: ${itemDetailResult.error}")
                                }
                                is ResultState.Loading -> {
                                    // Handle loading state if needed
                                }
                                else -> {}
                            }
                        })
                    } ?: run {
                        binding.lowestInStock.visibility = View.GONE
                    }
                }
                else -> {}
            }
        })

        return binding.root
    }

    private fun formatDateString(dateString: String?): String {
        return if (dateString != null) {
            val offsetDateTime = OffsetDateTime.parse(dateString)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            offsetDateTime.format(formatter)
        } else {
            ""
        }
    }
}