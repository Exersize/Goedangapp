package com.example.goedangapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goedangapp.R
import com.example.goedangapp.databinding.ItemStockBinding
import com.example.goedangapp.response.ItemResponseItem

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items: List<ItemResponseItem> = listOf()

    class ItemViewHolder(val binding: ItemStockBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvJenis.text = item.name
        holder.binding.tvQty.text = item.quantity.toString()
        holder.binding.tvWeight.text = item.measuringUnit.toString()
        if (item.quantity!! <= item.threshold!!) {
            holder.binding.itemIcon.setImageResource(R.drawable.low_in_stock)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<ItemResponseItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}