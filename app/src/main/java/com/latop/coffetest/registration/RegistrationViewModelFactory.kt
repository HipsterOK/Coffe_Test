package com.latop.coffetest.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegistrationViewModelFactory(private val registrationRepository: RegistrationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(registrationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}