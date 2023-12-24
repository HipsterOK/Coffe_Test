package com.latop.coffetest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequestBody(
    val login: String, val password: String
)

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    companion object {
        // Метод для создания экземпляра ApiService
        fun create(): ApiService {
            return RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
}
