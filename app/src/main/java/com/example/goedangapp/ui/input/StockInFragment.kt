package com.example.goedangapp.ui.input

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.goedangapp.DashboardFragment
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentStockInBinding
import com.example.goedangapp.util.ResultState
import kotlinx.coroutines.launch

class StockInFragment : Fragment() {
    private var _binding: FragmentStockInBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private var selectedItem: String? = null
    private lateinit var adapterItems: ArrayAdapter<String>
    private val viewModel: StockInViewModel by viewModels<StockInViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeItemNames()

        binding.buttonAddItem.setOnClickListener {
            val intent = Intent(requireContext(), AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.qtyInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No use
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No use
            }

            override fun afterTextChanged(p0: Editable?) {
                updateTotal()
            }
        })

        binding.priceInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No use
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No use
            }

            override fun afterTextChanged(p0: Editable?) {
                updateTotal()
            }
        })

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch {
                val itemId = viewModel.fetchItemId(selectedItem)
                val userId = viewModel.getUserId()

                val quantityStr = binding.qtyInputEdit.text.toString()
                val priceStr = binding.priceInputEdit.text.toString()

                if (quantityStr.isBlank() || priceStr.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val quantity = quantityStr.toInt()
                    val price = priceStr.toInt()
                    val total = quantity * price

                    viewModel.addItemEntry(
                        itemId,
                        userId,
                        "in",
                        quantity,
                        price,
                        total
                    ).observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is ResultState.Success -> {
                                    Toast.makeText(
                                        requireContext(),
                                        result.data.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    refreshContent()
                                }

                                is ResultState.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        result.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    refreshContent()
                                }

                                else -> {}
                            }
                        }
                    }
                }
            }
        }
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

    private fun updateTotal() {
        val quantityStr = binding.qtyInputEdit.text.toString()
        val priceStr = binding.priceInputEdit.text.toString()

        if (quantityStr.isNotBlank() || priceStr.isNotBlank()) {
            val quantity = quantityStr.toIntOrNull()?: 0
            val price = priceStr.toIntOrNull()?: 0
            val total = quantity * price
            binding.totalInputEdit.setText(total.toString())
        }
    }

    private fun setupItemAdapter(itemNames: List<String>) {
        adapterItems = ArrayAdapter<String>(requireContext(), R.layout.list_item, itemNames)
        binding.autoCompleteText.setAdapter(adapterItems)
        binding.autoCompleteText.setOnItemClickListener { adapterView, view, i, l ->
            selectedItem = adapterView.getItemAtPosition(i).toString()
        }
    }



    fun refreshContent() {
        binding.qtyInputEdit.setText("")
        binding.priceInputEdit.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}