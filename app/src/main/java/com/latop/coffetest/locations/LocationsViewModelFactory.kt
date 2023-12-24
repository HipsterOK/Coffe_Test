package com.latop.coffetest.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationsViewModelFactory(private val loginRepository: LocationsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationsViewModel::class.java)) {
            return LocationsViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}