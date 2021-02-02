package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edgar.forgethelper.R
import com.edgar.forgethelper.databinding.ActivityMainBinding

/**
 * MainActivity.kt
 * Display Four buttons with Logo at the top
 * Button 1: Locations -> Lead to LocationActivity
 * Button 2: Search -> Lead to SearchActivity
 * Button 3: Cloud -> Display cloud options
 * Button 4: Exit -> Turn off the application
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpButtons()
    }

    private fun setUpButtons() {
        binding.btnLocations.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
        binding.btnCloud.setOnClickListener(this)
        binding.btnExit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                binding.btnLocations -> openLocationActivity()
                binding.btnSearch -> openSearchActivity()
                binding.btnCloud -> displayCloudMenu()
                binding.btnExit -> finish()
            }
        }
    }

    private fun openLocationActivity() {
        val i = Intent(this, LocationActivity::class.java)
        startActivity(i)
    }

    private fun openSearchActivity() {
        TODO("Not yet implemented")
    }

    private fun displayCloudMenu() {
        TODO("Not yet implemented")
    }
}
