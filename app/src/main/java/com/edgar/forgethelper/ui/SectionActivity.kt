package com.edgar.forgethelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgar.forgethelper.R
import com.edgar.forgethelper.adapter.OnItemClickListener
import com.edgar.forgethelper.adapter.SectionAdapter
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.databinding.ActivitySectionBinding
import com.edgar.forgethelper.utils.BottomSheetHelper
import com.edgar.forgethelper.utils.displayBottomSheet
import com.edgar.forgethelper.utils.makeAlert
import com.edgar.forgethelper.viewmodel.SectionViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * SectionActivity.kt
 * Display sections from the database with selected location id from LocationActivity
 * User can add, update, or delete a section and go to ItemActivity
 */

class SectionActivity : AppCompatActivity(), OnItemClickListener<MSection> {
    private lateinit var binding: ActivitySectionBinding
    private lateinit var viewModel: SectionViewModel
    private val locationId: Long by lazy { intent.getLongExtra("location_id", -1) }
    private val locationName: String by lazy { intent.getStringExtra("location_name")!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initiate SectionViewModel
        val factory = SectionViewModel.Companion.Factory(application, locationId)
        viewModel =
            ViewModelProvider(viewModelStore, factory).get(SectionViewModel::class.java)

        setToolbar()
        fillRecyclerView()
        binding.sectionAddBtn.setOnClickListener { addSection() }

        subscribeErrorMessage()
    }

    /**
     * toolbar-related functions
     */
    private fun setToolbar() {
        binding.sectionToolbar.title = locationName
        setSupportActionBar(binding.sectionToolbar)
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
     *  - observe sections
     */
    private fun fillRecyclerView() {
        val adapter = SectionAdapter(this)

        binding.sectionRv.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@SectionActivity)
            setHasFixedSize(true)
        }

        viewModel.sections.observe(this) {
            adapter.setSections(it)
        }
    }

    // Display BottomSheet where user can enter section name
    private fun addSection() = displayBottomSheet(
        BottomSheetHelper(
            title = getString(R.string.add_section),
            guide = getString(R.string.update_section_guide),
            btnText = getString(R.string.add_button),
            manager = supportFragmentManager,
            tag = "ADD SECTION"
        )
    ) {
        // when the button in the BottomSheet is clicked, insert a section
        val newSection = MSection(name = it, location_id = locationId)
        viewModel.insertSection(newSection)
    }

    // Lead to Item Activity - putExtra location id, section id and name
    override fun onNameClick(item: MSection) {
        val i = Intent(this@SectionActivity, ItemActivity::class.java)
        i.putExtra("location_id", item.location_id)
        i.putExtra("location_name", locationName)
        i.putExtra("section_id", item.id)
        i.putExtra("section_name", item.name)
        startActivity(i)
    }

    // Display BottomSheet where user can enter new section name
    override fun onUpdateClick(item: MSection) = displayBottomSheet(
        BottomSheetHelper(
            title = getString(R.string.add_section),
            guide = getString(R.string.update_section_guide),
            btnText = getString(R.string.add_button),
            manager = supportFragmentManager,
            tag = "UPDATE SECTION"
        )
    ) {
        item.name = it
        viewModel.insertSection(item)
    }

    // Display an alert dialog -> delete or cancel
    override fun onDeleteClick(item: MSection) {
        makeAlert(
            this@SectionActivity,
            getString(R.string.delete_section_dialog_title),
            getString(R.string.delete_section_dialog_message)
        ) { _, _ -> viewModel.deleteSection(item) }
    }

    /**
     * Observe errorMessage from LocationViewModel
     * show snackbar if errorMessage is not null
     */
    private fun subscribeErrorMessage() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.sectionRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }
    }
}