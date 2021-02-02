package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.ItemAdapter
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.databinding.ActivitySearchBinding
import com.edgar.forgethelper.viewmodel.ItemViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize ViewModel
        val factory = ItemViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(ItemViewModel::class.java)

        setToolbar()
        binding.searchBtn.setOnClickListener {
            fillRecyclerView()
        }
    }

    private fun setToolbar() {
        binding.searchToolbar.setTitle(R.string.search)
        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillRecyclerView() {
        val adapter = ItemAdapter { item -> openDetailItemActivity(item) }

        binding.recyclerViewSearch.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
        }
        viewModel.getSearchResult(binding.searchEt.text.toString()).observe(this) {
            adapter.setItems(it)
        }
    }

    // Open DetailItemActivity and putExtra necessary information
    private fun openDetailItemActivity(item: MItem) {
        val i = Intent(this@SearchActivity, DetailItemActivity::class.java)
        i.putExtra("item_id", item.id)
        startActivity(i)
    }
}