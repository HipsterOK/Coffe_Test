package com.latop.coffetest.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentMenuBinding
import com.latop.coffetest.locations.CafeListAdapter
import com.latop.coffetest.login.LoginRepository
import com.latop.coffetest.login.LoginViewModel
import com.latop.coffetest.login.LoginViewModelFactory
import com.latop.coffetest.network.ApiService

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val menuViewModel: MenuViewModel by lazy {
        ViewModelProvider(
            this, MenuViewModelFactory(MenuRepository(ApiService.create()))
        )[MenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")
        val id = arguments?.getInt("id")
        if (id != null) {
            menuViewModel.getMenu(token.toString(), id)
        }

        binding.goToLocation.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_locationsFragment, Bundle().apply {
                    putString("token", token)
                })
        }

        binding.goToOrder.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_locationsFragment, Bundle().apply {
                    putString("token", token)
                })
        }

        menuViewModel.menuResponse.observe(viewLifecycleOwner) { menu ->
            if (menu.isNotEmpty()) {
                val spanCount = 2
                val layoutManager = GridLayoutManager(requireContext(), spanCount)
                binding.recyclerView.layoutManager = layoutManager
                val adapter = MenuListAdapter(menu, menuViewModel)
                binding.recyclerView.adapter = adapter
            } else {
            }
        }


    }
}