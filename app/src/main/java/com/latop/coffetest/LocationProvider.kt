package com.latop.coffetest

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.latop.coffetest.network.Point

class LocationProvider(private val context: Context, private val listener: LocationListener) {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0L, 0f, listener
            )
        }
    }

    fun getLastKnownLocation(): Point? {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            val lastKnownLocation: Location? =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            return lastKnownLocation?.let {
                Point(it.latitude.toString(), it.longitude.toString())
            }
        }
        return null
    }

    fun removeLocationUpdates() {
        locationManager.removeUpdates(listener)
    }
}
