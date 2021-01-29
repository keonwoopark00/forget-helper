package com.edgar.forgethelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edgar.forgethelper.R
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.databinding.RvLocationBinding

// LocationAdapter.kt

class LocationAdapter(val listener: OnItemClickListener<MLocation>) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private var locations: List<MLocation> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvLocationBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_location, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setLocations(locations: List<MLocation>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: MLocation) {
            binding.location = location
            binding.rvLocNameBtn.setOnClickListener { listener.onNameClick(location) }
            binding.rvLocUpdateBtn.setOnClickListener { listener.onUpdateClick(location) }
            binding.rvLocDeleteBtn.setOnClickListener { listener.onDeleteClick(location) }
        }
    }
}