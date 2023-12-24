package com.latop.coffetest.network

import com.latop.coffetest.data.Location
import com.latop.coffetest.data.LoginRequestBody
import com.latop.coffetest.data.MenuItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    @GET("locations")
    suspend fun getLocations(@Header("Authorization") token: String): List<Location>

    @GET("/location/{id}/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String, @Path("id") cafeId: Int
    ): List<MenuItem>

    companion object {
        fun create(): ApiService {
            return RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
}
