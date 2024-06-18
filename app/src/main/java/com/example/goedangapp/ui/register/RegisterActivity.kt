package com.example.goedangapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goedangapp.NetworkUtils
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.ActivityRegisterBinding
import com.example.goedangapp.ui.login.LoginActivity
import com.example.goedangapp.util.ResultState
import com.example.goedangapp.util.showLoading
import com.example.goedangapp.util.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if (!NetworkUtils.NetworkUtils.isNetworkAvailable(this)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                binding.progressIndicator.showLoading(true)
                            }

                            is ResultState.Success -> {
                                showToast(result.data.message)
                                binding.progressIndicator.showLoading(false)
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }

                            is ResultState.Error -> {
                                showToast(result.error)
                                binding.progressIndicator.showLoading(false)
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }
}