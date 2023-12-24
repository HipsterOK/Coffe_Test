package com.latop.coffetest.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentMenuBinding
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
            val selectedMenuItems = menuViewModel.menuItems.value?.filter { it.count > 0 }

            println(selectedMenuItems)
            println(menuViewModel.menuItems.value)
            println(menuViewModel.menuResponse.value)

            if (selectedMenuItems?.isNotEmpty() == true) {
                val bundle = Bundle().apply {
                    putString("token", token)
                    putParcelableArrayList("selectedMenuItems", ArrayList(selectedMenuItems))
                    id?.let { it1 -> putInt("id", it1) }
                }
                Navigation.findNavController(view)
                    .navigate(R.id.action_menuFragment_to_orderFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Корзина пуста!", Toast.LENGTH_SHORT).show()
            }
        }

        menuViewModel.menuResponse.observe(viewLifecycleOwner) { menu ->
            if (menu.isNotEmpty()) {
                val spanCount = 2
                val layoutManager = GridLayoutManager(requireContext(), spanCount)
                binding.recyclerView.layoutManager = layoutManager
                val adapter = MenuListAdapter(menu, menuViewModel)
                binding.recyclerView.adapter = adapter
            } else {
                Toast.makeText(requireContext(), "Пустое меню!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}