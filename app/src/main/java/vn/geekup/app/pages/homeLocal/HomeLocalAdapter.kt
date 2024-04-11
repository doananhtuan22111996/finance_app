package vn.geekup.app.pages.homeLocal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import vn.geekup.app.databinding.ItemPagingBinding
import vn.geekup.domain.model.ItemModel

class HomeLocalAdapter : PagingDataAdapter<ItemModel, HomeLocalAdapter.ItemViewHolder>(
    differCallback
) {

    companion object {
        val differCallback = object : ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                // Use == because ItemModel is Data Class
                return oldItem == newItem
            }
        }
    }

    inner class ItemViewHolder(private val viewBinding: ItemPagingBinding) :
        ViewHolder(viewBinding.root) {
        fun bind(data: ItemModel?) {
            viewBinding.item = data
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPagingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}