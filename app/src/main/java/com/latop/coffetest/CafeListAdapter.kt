package com.latop.coffetest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CafeListAdapter(private val dataList: List<Pair<String, String>>) :
    RecyclerView.Adapter<CafeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cafe, parent, false)
        return CafeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CafeListViewHolder, position: Int) {
        holder.name.text = dataList[position].first
        holder.distance.text = dataList[position].first
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}