package com.example.goedangapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goedangapp.MainActivity
import com.example.goedangapp.NetworkUtils
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.ActivityLoginBinding
import com.example.goedangapp.model.UserModel
import com.example.goedangapp.response.LoginResponse
import com.example.goedangapp.ui.register.RegisterActivity
import com.example.goedangapp.util.ResultState
import com.example.goedangapp.util.showLoading
import com.example.goedangapp.util.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (!NetworkUtils.NetworkUtils.isNetworkAvailable(this)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.login(
                    binding.edLoginEmail.text.toString(),
                    binding.edLoginPassword.text.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                binding.progressIndicator.showLoading(true)
                            }

                            is ResultState.Success -> {
                                val loginResponse = result.data as LoginResponse
                                loginResponse.name?.let { it1 ->
                                    loginResponse.token?.let { it2 ->
                                        UserModel(
                                            it1,
                                            binding.edLoginEmail.text.toString(),
                                            it2
                                        )
                                    }
                                }?.let { it2 ->
                                    viewModel.saveUser(
                                        it2
                                    )
                                }
                                // You need to define a message property in the LoginResponse class
                                // or get the message from another source
                                // showToast(loginResponse.message)
                                binding.progressIndicator.showLoading(false)
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
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