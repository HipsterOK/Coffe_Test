package com.latop.coffetest.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentLocationsBinding
import com.latop.coffetest.login.LoginRepository
import com.latop.coffetest.login.LoginViewModel
import com.latop.coffetest.login.LoginViewModelFactory
import com.latop.coffetest.network.ApiService

class LocationsFragment : Fragment() {

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
                val customAdapter = CafeListAdapter(requireContext(), locations)
                binding.coffeList.layoutManager = LinearLayoutManager(requireContext())
                binding.coffeList.adapter = customAdapter
            } else {
            }
        }

    }

}