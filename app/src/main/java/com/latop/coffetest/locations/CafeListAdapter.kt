package com.latop.coffetest.locations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latop.coffetest.LocationProvider
import com.latop.coffetest.MyLocationListener
import com.latop.coffetest.databinding.ItemCafeBinding
import com.latop.coffetest.network.Location
import com.latop.coffetest.network.Point
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class CafeListAdapter(
    private val context: Context,
    private val dataList: List<Location>,
) : RecyclerView.Adapter<CafeListAdapter.CafeListViewHolder>() {

    private lateinit var binding: ItemCafeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeListViewHolder {
        binding = ItemCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CafeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeListViewHolder, position: Int) {
        val cafe = dataList[position]

        val locationProvider = LocationProvider(context, MyLocationListener { _ ->
            // Handle user location update
        })
        locationProvider.requestLocationUpdates()
        val userLocation = locationProvider.getLastKnownLocation()
        locationProvider.removeLocationUpdates()

        val distance = userLocation?.let { calculateDistance(cafe.point, it) }
        val formattedDistance = distance?.let { getFormattedDistance(it) }
        holder.bind(cafe, formattedDistance.toString())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class CafeListViewHolder(private val binding: ItemCafeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cafe: Location, formattedDistance: String) {
            binding.name.text = cafe.name
            binding.distance.text = formattedDistance
        }
    }

    private fun calculateDistance(cafePoint: Point, userLocation: Point): Double {
        val earthRadius = 6371

        val latDistance =
            Math.toRadians(cafePoint.latitude.toDouble() - userLocation.latitude.toDouble())
        val lonDistance =
            Math.toRadians(cafePoint.longitude.toDouble() - userLocation.longitude.toDouble())

        val a =
            sin(latDistance / 2) * sin(latDistance / 2) + cos(Math.toRadians(userLocation.latitude.toDouble())) * cos(
                Math.toRadians(
                    cafePoint.latitude.toDouble()
                )
            ) * sin(lonDistance / 2) * sin(lonDistance / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    private fun getFormattedDistance(distance: Double): String {
        return if (distance < 1) {
            "${(distance * 1000).roundToInt()} м от вас"
        } else {
            String.format("${distance.toInt()} км от вас")
        }
    }
}
