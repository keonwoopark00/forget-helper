package com.edgar.forgethelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.OnItemClickListener
import com.edgar.forgethelper.adapter.SectionAdapter
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.databinding.ActivitySectionBinding

/**
 * SectionActivity.kt
 * Display sections from the database with selected location id from LocationActivity
 * User can add, update, or delete a section and go to ItemActivity
 */

class SectionActivity : AppCompatActivity(), OnItemClickListener<MSection> {
    private lateinit var binding: ActivitySectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        fillRecyclerView()
        binding.sectionAddBtn.setOnClickListener { addSection() }
    }

    private fun setToolbar() {
        // TODO("Toolbar title should be the selected location name")
        binding.sectionToolbar.setTitle(R.string.locations)
        setSupportActionBar(binding.sectionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addSection() {
        TODO("Display BottomSheet where user can enter section name")
    }

    private fun fillRecyclerView() {
        val adapter = SectionAdapter(this)

        binding.sectionRv.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@SectionActivity)
            setHasFixedSize(true)
        }

        TODO("Set Sections from viewModel(adapter.setSections())")
    }

    override fun onNameClick(item: MSection) {
        TODO("Lead to Item Activity with right information (section_id)")
    }

    override fun onUpdateClick(item: MSection) {
        TODO("Display BottomSheet where user can enter new section name")
    }

    override fun onDeleteClick(item: MSection) {
        TODO("Display an alert dialog -> delete or cancel")
    }
}