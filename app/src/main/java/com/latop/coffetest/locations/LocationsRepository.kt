package com.latop.coffetest.locations

import com.latop.coffetest.network.ApiService
import com.latop.coffetest.network.Location
import com.latop.coffetest.network.LoginRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class LocationsRepository(private val apiService: ApiService) {
    suspend fun getLocations(token: String): List<Location> {
        return try {
            apiService.getLocations("Bearer $token")
        } catch (e: Exception) {
            emptyList()
        }
    }
}