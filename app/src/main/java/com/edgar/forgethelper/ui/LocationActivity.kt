package com.edgar.forgethelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.LocationAdapter
import com.edgar.forgethelper.adapter.OnItemClickListener
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.databinding.ActivityLocationBinding

/**
 * LocationActivity.kt
 * Display all locations from the database
 * User can add, update, or delete a location and go to SectionActivity
 */

class LocationActivity : AppCompatActivity(), OnItemClickListener<MLocation> {
    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        fillRecyclerView()
        binding.locationAddBtn.setOnClickListener { addLocation() }
    }

    private fun setToolbar() {
        binding.locationToolbar.setTitle(R.string.locations)
        setSupportActionBar(binding.locationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addLocation() {
        TODO("Display BottomSheet where user can enter location name")
    }

    private fun fillRecyclerView() {
        val adapter = LocationAdapter(this)

        binding.locationRv.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@LocationActivity)
            setHasFixedSize(true)
        }

        TODO("Set Locations from viewModel(adapter.setLocations())")
    }

    override fun onNameClick(item: MLocation) {
        TODO("Lead to Section Activity with right information (location_id)")
    }

    override fun onUpdateClick(item: MLocation) {
        TODO("Display BottomSheet where user can enter new location name")
    }

    override fun onDeleteClick(item: MLocation) {
        TODO("Display an alert dialog -> delete or cancel")
    }
}