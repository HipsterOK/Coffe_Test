package com.latop.coffetest.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _isOrderCompleted = MutableLiveData(false)
    val isOrderCompleted: LiveData<Boolean> get() = _isOrderCompleted

    fun setOrderCompleted(completed: Boolean) {
        _isOrderCompleted.value = completed
    }
}
