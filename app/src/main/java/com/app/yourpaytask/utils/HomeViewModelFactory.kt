package com.app.yourpaytask.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.yourpaytask.repository.HomeRepository
import com.app.yourpaytask.viewmodel.HomeViewModel


class HomeViewModelFactory (
    private val application: Application,
    private val repository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(application,repository) as T
    }
}