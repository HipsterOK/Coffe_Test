package com.latop.coffetest.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latop.coffetest.data.MenuItem
import kotlinx.coroutines.launch

class MenuViewModel(private val menuRepository: MenuRepository) : ViewModel() {

    private val _menuResponse = MutableLiveData<List<MenuItem>>()
    val menuResponse: LiveData<List<MenuItem>> get() = _menuResponse
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> get() = _menuItems

    init {
        _menuItems.value = menuResponse.value
    }

    fun updateMenuItem(menuItem: MenuItem) {
        val items = _menuItems.value?.toMutableList() ?: mutableListOf()
        val index = items.indexOfFirst { it.id == menuItem.id }
        if (index != -1) {
            items[index] = menuItem
            _menuItems.value = items
        }
    }

    fun getMenu(token: String, id: Int) {
        viewModelScope.launch {
            val menu = menuRepository.getMenu(token, id)
            _menuResponse.value = menu
            _menuItems.value = menu
        }
    }
}