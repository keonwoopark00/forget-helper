package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.LocationAdapter
import com.edgar.forgethelper.adapter.OnItemClickListener
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.databinding.ActivityLocationBinding
import com.edgar.forgethelper.utils.BottomSheetHelper
import com.edgar.forgethelper.utils.displayBottomSheet
import com.edgar.forgethelper.utils.makeAlert
import com.edgar.forgethelper.viewmodel.LocationViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * LocationActivity.kt
 * Display all locations from the database
 * User can add, update, or delete a location and go to SectionActivity
 */

class LocationActivity : AppCompatActivity(), OnItemClickListener<MLocation> {
    private lateinit var binding: ActivityLocationBinding
    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initiate LocationViewModel
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel =
            ViewModelProvider(viewModelStore, factory).get(LocationViewModel::class.java)
        setToolbar()
        fillRecyclerView()
        binding.locationAddBtn.setOnClickListener { addLocation() }
        subscribeErrorMessage()
    }

    /**
     * toolbar-related functions
     */
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

    /**
     * setup recyclerView
     *  - set adapter
     *  - subscribe viewModel.locations
     */
    private fun fillRecyclerView() {
        val adapter = LocationAdapter(this)

        binding.locationRv.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@LocationActivity)
            setHasFixedSize(true)
        }

        viewModel.locations.observe(this, { value ->
            adapter.setLocations(value)
        })

    }

    // Display BottomSheet where user can enter location name
    private fun addLocation() = displayBottomSheet(
        BottomSheetHelper(
            title = getString(R.string.add_location),
            guide = getString(R.string.update_location_guide),
            btnText = getString(R.string.add_button),
            manager = supportFragmentManager,
            tag = "ADD LOCATION"
        )
    ) {
        // when the button in the BottomSheet is clicked, insert a location
        val newLocation = MLocation(name = it)
        viewModel.insertLocation(newLocation)
    }

    // Lead to Section Activity: putExtra location id and name
    override fun onNameClick(item: MLocation) {
        val i = Intent(this@LocationActivity, SectionActivity::class.java)
        i.putExtra("location_id", item.id)
        i.putExtra("location_name", item.name)
        startActivity(i)
    }

    // Display BottomSheet where user can enter new location name
    override fun onUpdateClick(item: MLocation) = displayBottomSheet(
        BottomSheetHelper(
            title = getString(R.string.update_location),
            guide = getString(R.string.update_location_guide),
            btnText = getString(R.string.update),
            manager = supportFragmentManager,
            tag = "UPDATE LOCATION"
        )
    ) {
        item.name = it
        viewModel.insertLocation(item)
    }

    // Display an alert dialog -> delete or cancel
    override fun onDeleteClick(item: MLocation) {
        makeAlert(
            this@LocationActivity,
            getString(R.string.delete_location_dialog_title),
            getString(R.string.delete_location_dialog_message)
        ) { _, _ -> viewModel.deleteLocation(item) }
    }

    /**
     * Observe errorMessage from LocationViewModel
     * show snackbar if errorMessage is not null
     */
    private fun subscribeErrorMessage() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.locationRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }
    }
}