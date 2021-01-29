package com.edgar.forgethelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edgar.forgethelper.R
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.databinding.RvSectionBinding

// SectionAdapter.kt

class SectionAdapter(val listener: OnItemClickListener<MSection>) :
    RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    private var sections: List<MSection> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RvSectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.rv_section, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    fun setSections(sections: List<MSection>) {
        this.sections = sections
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: MSection) {
            binding.section = section
            binding.rvSecNameBtn.setOnClickListener { listener.onNameClick(section) }
            binding.rvSecUpdateBtn.setOnClickListener { listener.onUpdateClick(section) }
            binding.rvSecDeleteBtn.setOnClickListener { listener.onDeleteClick(section) }
        }
    }
}