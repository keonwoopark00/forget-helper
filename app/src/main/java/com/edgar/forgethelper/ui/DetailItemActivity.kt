package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.edgar.forgethelper.R
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.databinding.ActivityDetailItemBinding
import com.edgar.forgethelper.utils.BottomSheetHelper
import com.edgar.forgethelper.utils.displayBottomSheetItem
import com.edgar.forgethelper.utils.makeAlert
import com.edgar.forgethelper.viewmodel.ItemViewModel

/**
 * DetailItemActivity.kt
 * Display corresponding location name, section name, and item description
 * User can update or delete the item or share the item information
 */

class DetailItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItemBinding
    private lateinit var viewModel: ItemViewModel
    private val itemId: Long by lazy { intent.getLongExtra("item_id", -1) }
    private var detailItem: MItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initiate ItemViewModel
        val factory = ItemViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(ItemViewModel::class.java)

        // observe item detail
        viewModel.getItemDetail(itemId).observe(this) {
            detailItem = it
            setToolbar()
            setupTextViews()
        }
    }

    // Display right information to TextViews
    private fun setupTextViews() {
        binding.itemDetailLocation.text = detailItem?.location_name
        binding.itemDetailSection.text = detailItem?.section_name
        binding.itemDetailDescription.text = detailItem?.description
    }

    /**
     * Toolbar-related functions
     */
    private fun setToolbar() {
        binding.itemDetailToolbar.title = detailItem?.name
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

    // Display BottomSheet where user can enter new item name and description
    private fun updateItem() = displayBottomSheetItem(
        BottomSheetHelper(
            title = getString(R.string.update_item),
            btnText = getString(R.string.update),
            manager = supportFragmentManager,
            tag = "UPDATE ITEM"
        )
    ) { name: String, desc: String ->
        var item: MItem = detailItem!!.copy(name = name, description = desc)
        item.id = itemId
        viewModel.insertItem(item)
    }

    // Display an alert dialog -> delete or cancel
    private fun deleteItem() {
        makeAlert(
            this@DetailItemActivity,
            getString(R.string.delete_item_title),
            getString(R.string.delete_confirmation_message),
        ) { _, _ ->
            viewModel.deleteItem(detailItem!!)
            finish()
        }
    }

    // Display a share dialog
    private fun shareItemInformation() {
        val i = Intent()
        i.action = Intent.ACTION_SEND
        i.type = "text/plain"
        val text = "Location: ${detailItem!!.location_name}\nSection: ${detailItem!!.section_name}" +
                "\nItem: ${detailItem!!.name}"
        i.putExtra(Intent.EXTRA_TEXT, text)
        val chooser = Intent.createChooser(i, getString(R.string.share_title_message))
        startActivity(chooser)
    }
}