package com.kattabozor.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kattabozor.task.databinding.ItemPlaceholderProductBinding

class PlaceholderAdapter : RecyclerView.Adapter<PlaceholderAdapter.PVH>() {

    inner class PVH(itemBinding: ItemPlaceholderProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(ItemPlaceholderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {}

    override fun getItemCount(): Int = 8

}