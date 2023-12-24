package com.latop.coffetest

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.latop.coffetest.network.Point

class MyLocationListener(private val callback: (Point) -> Unit) : LocationListener {

    override fun onLocationChanged(location: Location) {
        // Called when the location has changed
        callback(Point(location.latitude.toString(), location.longitude.toString()))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Called when the provider status changes (e.g., GPS is enabled/disabled)
    }

    override fun onProviderEnabled(provider: String) {
        // Called when the provider is enabled
    }

    override fun onProviderDisabled(provider: String) {
        // Called when the provider is disabled
    }
}
