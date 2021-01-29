package com.edgar.forgethelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.edgar.forgethelper.R
import com.edgar.forgethelper.databinding.ActivityDetailItemBinding

/**
 * DetailItemActivity.kt
 * Display corresponding location name, section name, and item description
 * User can update or delete the item or share the item information
 */

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
    }

    private fun setToolbar() {
        // TODO("toolbar title should be the selected item name")
        binding.itemDetailToolbar.title = "Item Name"
        setSupportActionBar(binding.itemDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ind_item_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_update -> updateItem()
            R.id.menu_delete -> deleteItem()
            R.id.menu_share -> shareItemInformation()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        TODO("Display BottomSheet where user can enter new item name and description")
    }

    private fun deleteItem() {
        TODO("Display an alert dialog -> delete or cancle")
    }

    private fun shareItemInformation() {
        TODO("Display a share dialog")
    }
}