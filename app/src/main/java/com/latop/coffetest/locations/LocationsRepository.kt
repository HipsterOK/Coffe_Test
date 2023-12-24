package com.latop.coffetest.locations

import com.latop.coffetest.network.ApiService
import com.latop.coffetest.data.Location

class LocationsRepository(private val apiService: ApiService) {
    suspend fun getLocations(token: String): List<Location> {
        return try {
            apiService.getLocations("Bearer $token")
        } catch (e: Exception) {
            emptyList()
        }
    }
}