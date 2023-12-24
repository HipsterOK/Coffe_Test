package com.latop.coffetest.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latop.coffetest.databinding.ItemMenuBinding
import com.latop.coffetest.data.MenuItem

class MenuListAdapter(
    private val dataList: List<MenuItem>, private val viewModel: MenuViewModel
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

    private lateinit var binding: ItemMenuBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = dataList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MenuViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            binding.name.text = menuItem.name
            binding.price.text = "${menuItem.price} руб"
            binding.count.text = menuItem.count.toString()

            binding.plus.setOnClickListener {
                menuItem.count++
                binding.count.text = menuItem.count.toString()
                viewModel.updateMenuItem(menuItem)
            }

            binding.minus.setOnClickListener {
                if (menuItem.count > 0) {
                    menuItem.count--
                    binding.count.text = menuItem.count.toString()
                    viewModel.updateMenuItem(menuItem)
                }
            }

            Glide.with(binding.root).load(menuItem.imageURL).into(binding.image)
        }
    }
}
