package com.edgar.forgethelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edgar.forgethelper.R
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.databinding.RvItemBinding

// ItemAdapter.kt

class ItemAdapter(val onItemButtonClick: (MItem) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var items: List<MItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<MItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MItem) {
            binding.item = item
            binding.rvItemName.setOnClickListener { onItemButtonClick(item) }
        }
    }
}