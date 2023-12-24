package com.latop.coffetest.menu

import com.latop.coffetest.network.ApiService
import com.latop.coffetest.network.MenuItem

class MenuRepository(private val apiService: ApiService) {
    suspend fun getMenu(token: String, id: Int): List<MenuItem> {
        return try {
            apiService.getMenu("Bearer $token", id)
        } catch (e: Exception) {
            emptyList()
        }
    }
}