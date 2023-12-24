package com.latop.coffetest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.latop.coffetest.databinding.FragmentLocationsBinding

class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

}