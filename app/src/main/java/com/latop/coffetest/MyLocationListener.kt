package com.latop.coffetest

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.latop.coffetest.data.Point

class MyLocationListener(private val callback: (Point) -> Unit) : LocationListener {
    override fun onLocationChanged(location: Location) {
        callback(Point(location.latitude.toString(), location.longitude.toString()))
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }
    override fun onProviderEnabled(provider: String) {
    }
    override fun onProviderDisabled(provider: String) {
    }
}
