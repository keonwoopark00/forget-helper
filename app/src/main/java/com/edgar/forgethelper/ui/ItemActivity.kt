package com.edgar.forgethelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.ItemAdapter
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.databinding.ActivityItemBinding

/**
 * ItemActivity.kt
 * Display all item names from the database with selected section id from SectionActivity
 * User can add an item and go to DetailItemActivity
 */

class ItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        fillRecyclerView()
    }

    private fun setToolbar() {
        // TODO("Toolbar title should be name of the selected section")
        binding.itemToolbar.setTitle(R.string.locations)
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
            R.id.menu_add -> { addItem() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addItem() {
        TODO("Display BottomSheet where user can enter item name and description")
    }

    private fun fillRecyclerView() {
        val adapter = ItemAdapter { item -> openDetailItemActivity(item) }

        binding.rvItem.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ItemActivity)
            setHasFixedSize(true)
        }

        TODO("Set Items from viewModel(adapter.setItems())")
    }

    private fun openDetailItemActivity(item: MItem) {
        TODO("Open DetailItemActivity and putExtra necessary information")
    }
}