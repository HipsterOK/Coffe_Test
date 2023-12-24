package com.latop.coffetest.login

import com.latop.coffetest.ApiService
import com.latop.coffetest.LoginRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(login: String, password: String): Response<ResponseBody> {
        val requestBody = LoginRequestBody(login, password)
        return apiService.login(requestBody)
    }
}