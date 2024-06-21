package com.example.goedangapp.ui.sales

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.ActivityLoginBinding
import com.example.goedangapp.databinding.ActivitySalesDetailBinding
import com.example.goedangapp.ui.inventory.ItemEntryAdapter
import com.example.goedangapp.ui.inventory.RecentAddViewModel
import com.example.goedangapp.ui.login.LoginViewModel
import com.example.goedangapp.util.ResultState

class SalesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySalesDetailBinding
    private val viewModel by viewModels<RecentAddViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getStringExtra("itemId")
        binding = ActivitySalesDetailBinding.inflate(layoutInflater)

        val itemEntryAdapter = ItemEntryAdapter(this, emptyList(), viewModel)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemEntryAdapter
        }

        viewModel.getItemEntriesById(itemId!!).observe(this, Observer { result ->
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

        val backButton = binding.backButton
        backButton.setOnClickListener {
            finish() // Navigate back to the previous activity or fragment
        }

        setContentView(binding.root)
        supportActionBar?.hide()

    }
}