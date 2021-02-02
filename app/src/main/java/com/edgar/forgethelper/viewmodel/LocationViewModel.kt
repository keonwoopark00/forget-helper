package com.edgar.forgethelper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.data.repository.DataHandleError
import com.edgar.forgethelper.data.repository.DataRepository
import com.edgar.forgethelper.data.repository.LocationRepository
import kotlinx.coroutines.launch

// LocationViewModel.kt

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    // error message when error happens
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    /**
     * LocationRepository-related objects
     */
    private val repository: DataRepository = DataRepository(application)
    private val locationRepo: LocationRepository
        get() = repository.getLocationRepo()

    val locations = locationRepo.locations

    fun insertLocation(location: MLocation) = launchDataHandler {
        locationRepo.insertLocation(location)
    }

    fun deleteLocation(location: MLocation) = launchDataHandler {
        locationRepo.deleteLocation(location)
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

