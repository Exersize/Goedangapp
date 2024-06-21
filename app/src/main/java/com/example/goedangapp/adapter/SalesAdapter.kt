import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goedangapp.databinding.ItemSalesBinding
import com.example.goedangapp.repository.CustomData
import com.example.goedangapp.util.OnItemClickListener

class SalesAdapter(private val context: Context, private var items: List<CustomData>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<SalesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemSalesBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            // Set click listener on the root view of the item layout
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // Get the clicked item's position
            val position = adapterPosition
            // Check if the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Retrieve the clicked item from the list
                val item = items[position]
                // Notify the click listener of the item click event
                itemClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvJenis.text = item.name
        holder.binding.tvTotal.text = item.total.toString()
        holder.binding.tvMeasurementUnit.text = item.measuringUnit
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<CustomData>) {
        items = newItems
        notifyDataSetChanged()
    }
}


