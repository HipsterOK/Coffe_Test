package com.latop.coffetest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CafeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val distance: TextView = itemView.findViewById(R.id.distance)
}