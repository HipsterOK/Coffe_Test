package com.latop.coffetest.registration

import com.latop.coffetest.network.ApiService
import com.latop.coffetest.network.LoginRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class RegistrationRepository(private val apiService: ApiService) {
    suspend fun register(login: String, password: String): Response<ResponseBody> {
        val requestBody = LoginRequestBody(login, password)
        return apiService.register(requestBody)
    }
}