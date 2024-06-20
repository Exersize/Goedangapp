package com.example.goedangapp.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goedangapp.R
import com.example.goedangapp.databinding.ListItemBinding
import com.example.goedangapp.response.ItemEntryItem
import com.example.goedangapp.util.ResultState
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ItemEntryAdapter(private var items: List<ItemEntryItem>, private val viewModel: RecentAddViewModel) : RecyclerView.Adapter<ItemEntryAdapter.ItemEntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemEntryViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemEntryViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ItemEntryViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemEntryItem) {
            viewModel.getItemById(item.itemId!!).observeForever{ result ->
                when(result) {
                    is ResultState.Success -> {
                        binding.tvJenis.text = result.data.name
                        binding.tvWeight.text = result.data.measuringUnit
                    }
                    is ResultState.Error -> {
                        binding.tvJenis.text = "Unknown"
                        binding.tvWeight.text = "xx"

                    }
                    is ResultState.Loading -> {
                        // Optionally handle loading state
                    }
                    else -> {}
                }
            }

            if (item.inOut == "in") {
                binding.tvStock.text = "Stock In"
                binding.tvStock.setBackgroundResource(R.drawable.tv_bg_green)
            } else {
                binding.tvStock.text = "Stock Out"
                binding.tvStock.setBackgroundResource(R.drawable.tv_bg_red)
            }

            binding.tvQty.text = item.quantity
            binding.tvDate.text = formatDateString(item.createdAt)
        }
    }

    private fun formatDateString(dateString: String?): String {
        return if (dateString != null) {
            val offsetDateTime = OffsetDateTime.parse(dateString)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            offsetDateTime.format(formatter)
        } else {
            ""
        }
    }
    fun updateItems(newItems: List<ItemEntryItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
