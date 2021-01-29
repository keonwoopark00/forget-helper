package com.edgar.forgethelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.edgar.forgethelper.R
import com.edgar.forgethelper.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        binding.searchBtn.setOnClickListener { fillRecyclerView() }
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
        TODO("with ItemAdapter")
    }
}