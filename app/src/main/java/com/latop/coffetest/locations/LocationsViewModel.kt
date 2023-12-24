package com.latop.coffetest.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latop.coffetest.network.Location
import kotlinx.coroutines.launch
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point

class LocationsViewModel(private val locationsRepository: LocationsRepository) : ViewModel() {

    private val _locationsResponse = MutableLiveData<List<Location>>()
    val locationsResponse: LiveData<List<Location>> get() = _locationsResponse

    fun getLocations(token: String) {
        viewModelScope.launch {
            val locations = locationsRepository.getLocations(token)
            _locationsResponse.value = locations
        }
    }

    override fun onCleared() {
        super.onCleared()
        MapKitFactory.getInstance().onStop()
    }
}