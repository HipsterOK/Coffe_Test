package com.latop.coffetest.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentOrderBinding
import com.latop.coffetest.data.MenuItem
import java.util.ArrayList

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            this, OrderViewModelFactory()
        )[OrderViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")
        val selectedMenuItems = arguments?.getParcelableArrayList<MenuItem>("selectedMenuItems")
        val id = arguments?.getInt("id")

        binding.goToMenu.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_orderFragment_to_menuFragment, Bundle().apply {
                    putString("token", token)
                    id?.let { it1 -> putInt("id", it1) }
                })
        }

        binding.pay.setOnClickListener {
            orderViewModel.setOrderCompleted(true)
            updateAdapter(selectedMenuItems)
            binding.payed.visibility = View.VISIBLE
            binding.orderList.isEnabled = false
        }

        if (selectedMenuItems?.isNotEmpty() == true) {
            val customAdapter = OrderListAdapter(selectedMenuItems, orderViewModel)
            binding.orderList.layoutManager = LinearLayoutManager(requireContext())
            binding.orderList.adapter = customAdapter
        } else {
            Toast.makeText(requireContext(), "Пустая корзина!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateAdapter(selectedMenuItems: ArrayList<MenuItem>?) {
        if (selectedMenuItems?.isNotEmpty() == true) {
            val customAdapter = OrderListAdapter(selectedMenuItems, orderViewModel)
            binding.orderList.layoutManager = LinearLayoutManager(requireContext())
            binding.orderList.adapter = customAdapter
        } else {
            Toast.makeText(requireContext(), "Пустая корзина!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}