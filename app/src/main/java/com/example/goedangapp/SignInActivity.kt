package com.example.goedangapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import com.example.goedangapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Placeholder simpel untuk pindah Activity
        // TODO : Integrasi Google Sign In
        binding.signInText.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            signIn()
        }
    }

    private fun signIn() {
        val credentialManager = CredentialManager.create(this)
        val googleIdOption = GetGoogle
    }
}