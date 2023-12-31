package com.latop.coffetest.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentLocationsBinding
import com.latop.coffetest.network.ApiService

class LocationsFragment : Fragment(), CafeItemClickListener {

    private lateinit var binding: FragmentLocationsBinding
    private val locationsViewModel: LocationsViewModel by lazy {
        ViewModelProvider(
            this, LocationsViewModelFactory(LocationsRepository(ApiService.create()))
        )[LocationsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")
        locationsViewModel.getLocations(token.toString())

        binding.goToMap.setOnClickListener {
            val cafes = locationsViewModel.locationsResponse.value ?: emptyList()
            val bundle = Bundle().apply {
                putParcelableArray("cafes", cafes.toTypedArray())
                putString("token", token)
            }

            Navigation.findNavController(view)
                .navigate(R.id.action_locationsFragment_to_mapFragment, bundle)
        }


        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_locationsFragment_to_loginFragment)
        }

        locationsViewModel.locationsResponse.observe(viewLifecycleOwner) { locations ->
            if (locations.isNotEmpty()) {
                val customAdapter =
                    CafeListAdapter(requireContext(), token.toString(), locations, this)
                binding.coffeList.layoutManager = LinearLayoutManager(requireContext())
                binding.coffeList.adapter = customAdapter
            } else {
                Toast.makeText(requireContext(), "Нет доступных кафе!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onCafeItemClick(token: String, cafeId: Int) {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_locationsFragment_to_menuFragment, Bundle().apply {
                    putString("token", token)
                    putInt("id", cafeId)
                })
        }
    }

}