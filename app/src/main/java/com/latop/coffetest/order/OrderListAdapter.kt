package com.latop.coffetest.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latop.coffetest.databinding.ItemOrderBinding
import com.latop.coffetest.data.MenuItem

class OrderListAdapter(
    private val dataList: List<MenuItem>, private val orderViewModel: OrderViewModel
) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    private lateinit var binding: ItemOrderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val menu = dataList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            binding.name.text = menuItem.name
            binding.price.text = "${menuItem.price} руб"
            binding.count.text = menuItem.count.toString()

            if (orderViewModel.isOrderCompleted.value == false) {
                binding.plus.setOnClickListener {
                    menuItem.count++
                    binding.count.text = menuItem.count.toString()
                }
                binding.minus.setOnClickListener {
                    if (menuItem.count > 0) {
                        menuItem.count--
                        binding.count.text = menuItem.count.toString()
                    }
                }
            }
        }
    }
}


