package com.latop.coffetest.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentMapBinding
import com.latop.coffetest.network.Location
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")

        binding.goToLoAtion.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_mapFragment_to_locationsFragment, Bundle().apply {
                    putString("token", token)
                })
        }

        var cafes = emptyList<Location>()
        arguments?.let {
            cafes = it.getParcelableArray("cafes")?.map { it as Location } ?: emptyList()
        }
        showCafesOnMap(token, binding.mapView, cafes)
    }

    private fun showCafesOnMap(token: String?, mapView: MapView, cafes: List<Location>) {
        for (cafe in cafes) {
            val cafePoint = Point(cafe.point.latitude.toDouble(), cafe.point.longitude.toDouble())

            val view =
                LayoutInflater.from(mapView.context).inflate(R.layout.map_placemark_layout, null)

            val iconImageView = view.findViewById<ImageView>(R.id.iconImageView)
            val nameTextView = view.findViewById<TextView>(R.id.nameTextView)

            iconImageView.setImageResource(R.drawable.icon)
            nameTextView.text = cafe.name

            val placemark = mapView.map.mapObjects.addPlacemark(cafePoint)

            placemark.addTapListener { _, _ ->
                Log.i("map", cafe.id.toString())
                navigateToCafeMenu(cafe.id, token)
                true
            }

            placemark.setView(ViewProvider(view))
        }
    }

    private fun navigateToCafeMenu(cafeId: Int, token: String?) {
        val bundle = Bundle().apply {
            putInt("id", cafeId)
            putString("token", token)
        }

        Navigation.findNavController(requireView())
            .navigate(R.id.action_mapFragment_to_menuFragment, bundle)
    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}