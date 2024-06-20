package com.example.goedangapp

import DashboardViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.repository.Injection
import com.example.goedangapp.ui.input.AddItemViewModel
import com.example.goedangapp.ui.input.StockInViewModel
import com.example.goedangapp.ui.input.StockInputViewModel
import com.example.goedangapp.ui.input.StockOutViewModel
import com.example.goedangapp.ui.inventory.CurrentStockViewModel
import com.example.goedangapp.ui.inventory.LowInStockViewModel
import com.example.goedangapp.ui.inventory.RecentAddViewModel
import com.example.goedangapp.ui.inventory.StockViewModel
import com.example.goedangapp.ui.login.LoginViewModel
import com.example.goedangapp.ui.profile.ProfileViewModel
import com.example.goedangapp.ui.register.RegisterViewModel
import com.example.goedangapp.ui.splash.SplashScreenViewModel

class ViewModelFactory (private val repository: GoedangRepo) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddItemViewModel::class.java) -> {
                AddItemViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StockInputViewModel::class.java) -> {
                StockInputViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StockInViewModel::class.java) -> {
                StockInViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StockOutViewModel::class.java) -> {
                StockOutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StockViewModel::class.java) -> {
                StockOutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CurrentStockViewModel::class.java) -> {
                CurrentStockViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LowInStockViewModel::class.java) -> {
                LowInStockViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RecentAddViewModel::class.java) -> {
                RecentAddViewModel(repository) as T
            }





            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}