package com.example.goedangapp.ui.input

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentStockInBinding
import com.example.goedangapp.util.ResultState

class StockInFragment : Fragment() {

    private var _binding: FragmentStockInBinding? = null
    private val binding get() = _binding!!
    private var placeholder: List<String> =
        listOf("lt", "kg", "pcs", "box", "roll", "pack", "sheet")
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    // TODO : Adapter items ambil dari items cloud
    private lateinit var adapterItems: ArrayAdapter<String>
    private val viewModel: StockInViewModel by viewModels<StockInViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddItem.setOnClickListener {
            val intent = Intent(requireContext(), AddItemActivity::class.java)
            startActivity(intent)
        }
        observeItemNames()
    }

    override fun onResume() {
        super.onResume()
        observeItemNames()
    }

    private fun observeItemNames() {
        viewModel.fetchItemName().observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    val itemNames = resultState.data
                    setupItemAdapter(itemNames)
                }
                is ResultState.Error -> {
                    val error = resultState.error
                    // Handle error state if needed
                }
                else -> {}
            }
        }
    }

    private fun setupItemAdapter(itemNames: List<String>) {
        adapterItems = ArrayAdapter<String>(requireContext(), R.layout.list_item, itemNames)
        binding.autoCompleteText.setAdapter(adapterItems)
        binding.autoCompleteText.setOnItemClickListener { adapterView, view, i, l ->
            val items = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireContext(), "Selected item: $items", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}