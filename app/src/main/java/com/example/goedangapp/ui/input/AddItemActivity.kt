package com.example.goedangapp.ui.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.ActivityAddItemBinding
import com.example.goedangapp.retrofit.UserPreference
import com.example.goedangapp.util.ResultState
import com.example.goedangapp.util.showToast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var adapterItems: ArrayAdapter<String>
    private var placeholder: List<String> =
        listOf("lt", "kg", "pcs", "box", "roll", "pack", "sheet")
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private var selectedItem: String? = null
    private val viewModel by viewModels<AddItemViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupItemAdapter()

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch {
                val userId = getUserId()
                val itemName = binding.itemnameAddEdit.text.toString()
                val measuringUnit = selectedItem ?: ""

                viewModel.addItem(
                    measuringUnit,
                    itemName,
                    0,
                    userId,
                ).observe(this@AddItemActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                showToast(result.data.message!!)
                                finish()
                            }
                            is ResultState.Error -> {
                                showToast(result.error)
                            }
                            else -> {
                                // Handle other states if needed
                            }
                        }
                    }
                }
            }
        }

        binding.buttonCancel.setOnClickListener{
             finish()
        }
    }

    private fun setupItemAdapter() {
        adapterItems = ArrayAdapter<String>(this, R.layout.list_measurement, placeholder)
        binding.autoCompleteText.setAdapter(adapterItems)
        binding.autoCompleteText.setOnItemClickListener { adapterView, view, i, l ->
            selectedItem = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(applicationContext, "Selected item: $selectedItem", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private suspend fun getUserId(): String {
        val userPreferences = UserPreference.getInstance(dataStore)
        val user = userPreferences.getUser().first()
        return user.userId
    }
}