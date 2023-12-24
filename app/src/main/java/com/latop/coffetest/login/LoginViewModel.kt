package com.latop.coffetest.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<Response<ResponseBody>>()
    val loginResponse: LiveData<Response<ResponseBody>> get() = _loginResponse

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = loginRepository.login(login, password)
        }
    }
}