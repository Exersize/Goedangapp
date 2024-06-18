package com.example.goedangapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.goedangapp.MainActivity
import com.example.goedangapp.NetworkUtils
import com.example.goedangapp.R
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.ActivitySplashScreenBinding
import com.example.goedangapp.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lifecycleScope.launch {
            delay(3000)
            if (!NetworkUtils.NetworkUtils.isNetworkAvailable(this@SplashScreenActivity)) {
                MaterialAlertDialogBuilder(this@SplashScreenActivity)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.getUser().observe(this@SplashScreenActivity) { user ->
                    if (!user.isLogin) {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                LoginActivity::class.java
                            )
                        )
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    }
                    finish()
                }
            }
        }
    }
}