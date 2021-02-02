package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.ItemAdapter
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.databinding.ActivityItemBinding
import com.edgar.forgethelper.utils.BottomSheetHelper
import com.edgar.forgethelper.utils.displayBottomSheetItem
import com.edgar.forgethelper.viewmodel.ItemViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * ItemActivity.kt
 * Display all item names from the database with selected section id from SectionActivity
 * User can add an item and go to DetailItemActivity
 */

class ItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemBinding
    private lateinit var viewModel: ItemViewModel
    private val locationId: Long by lazy { intent.getLongExtra("location_id", -1) }
    private val locationName: String by lazy { intent.getStringExtra("location_name")!! }
    private val sectionId: Long by lazy { intent.getLongExtra("section_id", -1) }
    private val sectionName: String by lazy { intent.getStringExtra("section_name")!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initiate ItemViewModel
        val factory = ItemViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(ItemViewModel::class.java)

        setToolbar()
        fillRecyclerView()

        subscribeErrorMessage()
    }

    /**
     * Toolbar-related functions
     */
    private fun setToolbar() {
        binding.itemToolbar.title = sectionName
        setSupportActionBar(binding.itemToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_add -> {
                addItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * setup recyclerView
     *  - set adapter
     *  - observe items
     */
    private fun fillRecyclerView() {
        val adapter = ItemAdapter { item -> openDetailItemActivity(item) }

        binding.rvItem.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ItemActivity)
            setHasFixedSize(true)
        }

        viewModel.getItemsBySectionId(sectionId).observe(this) {
            adapter.setItems(it)
        }
    }

    // Display BottomSheet where user can enter item name and description
    private fun addItem() = displayBottomSheetItem(
        BottomSheetHelper(
            title = getString(R.string.add_item),
            btnText = getString(R.string.add_button),
            manager = supportFragmentManager,
            tag = "ADD ITEM"
        )
    ) { name: String, desc: String ->
        val newItem =
            MItem(
                name = name,
                description = desc,
                location_id = locationId,
                location_name = locationName,
                section_id = sectionId,
                section_name = sectionName
            )
        viewModel.insertItem(newItem)
    }

    // Open DetailItemActivity and putExtra necessary information
    private fun openDetailItemActivity(item: MItem) {
        val i = Intent(this@ItemActivity, DetailItemActivity::class.java)
        i.putExtra("item_id", item.id)
        i.putExtra("selected_item", item)
        startActivity(i)
    }


    // when error happens, show snackbar
    private fun subscribeErrorMessage() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.itemRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }
    }
}