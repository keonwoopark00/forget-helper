package com.edgar.forgethelper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.data.repository.DataHandleError
import com.edgar.forgethelper.data.repository.DataRepository
import com.edgar.forgethelper.data.repository.SectionRepository
import kotlinx.coroutines.launch

// SectionViewModel.kt

class SectionViewModel(application: Application, locationId: Long) : AndroidViewModel(application) {

    companion object {
        class Factory(val application: Application, private val param: Long) :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SectionViewModel(application, param) as T
            }
        }
    }

    // error message when error happens
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    /**
     * SectionRepository-related objects
     */
    private val repository: DataRepository = DataRepository(application)
    private val sectionRepo: SectionRepository
        get() = repository.getSectionRepo()

    val sections = sectionRepo.getSectionsByLocationId(locationId)

    fun insertSection(section: MSection) = launchDataHandler {
        sectionRepo.insertSection(section)
    }

    fun deleteSection(section: MSection) = launchDataHandler {
        sectionRepo.deleteSection(section)
    }

    /**
     * Helper function to call a data-related functions with error handler
     */
    private fun launchDataHandler(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: DataHandleError) {
                _errorMessage.value = e.message
            }
        }
    }

    // called after the UI shows snackbar
    fun onSnackbarDisplayed() {
        _errorMessage.value = null
    }
}