package com.latop.coffetest.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class RegistrationViewModel(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    private val _registerResponse = MutableLiveData<Response<ResponseBody>>()
    val registerResponse: LiveData<Response<ResponseBody>> get() = _registerResponse

    fun register(login: String, password: String) {
        viewModelScope.launch {
            _registerResponse.value = registrationRepository.register(login, password)
        }
    }
}