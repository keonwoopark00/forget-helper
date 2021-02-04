package com.edgar.forgethelper.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.edgar.forgethelper.R
import com.edgar.forgethelper.databinding.ActivityMainBinding
import com.edgar.forgethelper.viewmodel.FirestoreViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

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
    private lateinit var viewModel: FirestoreViewModel
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // initiate viewModel
        val factory = FirestoreViewModel.Companion.Factory(application)
        viewModel = ViewModelProvider(viewModelStore, factory).get(FirestoreViewModel::class.java)

        setUpButtons()
        subscribeErrorMessage()
        subscribeSpinner()
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
        val i = Intent(this, SearchActivity::class.java)
        startActivity(i)
    }

    private fun displayCloudMenu() {
        dialog = Dialog(this@MainActivity, R.style.Dialog)
        dialog.setContentView(R.layout.cloud_dialog_layout)
        val btnImport: Button = dialog.findViewById(R.id.import_btn)
        btnImport.setOnClickListener{ showImportDialog()  }
        val btnExport: Button = dialog.findViewById(R.id.export_btn)
        btnExport.setOnClickListener{ showExportDialog() }
        dialog.show()
    }

    private fun showImportDialog() {
        // initiate dialog
        val importDialog = Dialog(this@MainActivity, R.style.Dialog)
        importDialog.setContentView(R.layout.import_dialog_layout)
        val btnImport: Button = importDialog.findViewById(R.id.import_import_btn)
        val etImportId: EditText = importDialog.findViewById(R.id.import_et_import_id)
        btnImport.setOnClickListener {
            viewModel.getDataFromFirestore(etImportId.text.toString())
            importDialog.dismiss()
        }
        importDialog.show()
        dialog.dismiss()
    }

    private fun showExportDialog() {
        // initiate dialog
        val exportDialog = Dialog(this@MainActivity, R.style.Dialog)
        exportDialog.setContentView(R.layout.export_dialog_layout)
        val btnExport: Button = exportDialog.findViewById(R.id.export_export_btn)
        val tvExportId: TextView = exportDialog.findViewById(R.id.export_import_id)
        val randomUuid = UUID.randomUUID().toString()
        tvExportId.text = randomUuid
        btnExport.setOnClickListener {
            viewModel.saveDataInFirestore(randomUuid)
            exportDialog.dismiss()
        }
        exportDialog.show()
        dialog.dismiss()
    }

    // when error happens, show snackbar
    private fun subscribeErrorMessage() {
        viewModel.errorMessage.observe(this) {
            it?.let {
                Snackbar.make(binding.mainRoot, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarDisplayed()
            }
        }
    }

    // show spinner during fetch
    private fun subscribeSpinner() {
        viewModel.spinner.observe(this) {
            it.let {
                binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}
