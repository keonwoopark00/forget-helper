package com.edgar.forgethelper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.edgar.forgethelper.data.repository.DataRepository
import com.edgar.forgethelper.data.repository.FirestoreRepository
import kotlinx.coroutines.launch

class FirestoreViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        class Factory(
            val application: Application,
        ) :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FirestoreViewModel(application) as T
            }
        }
    }

    // Show a loading spinner if true
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    // error message when error happens
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    // repository
    private val repository = DataRepository(application)
    private val firestoreRepo: FirestoreRepository
        get() = repository.getFirestoreRepo()


    fun saveDataInFirestore(uuid: String) {
        viewModelScope.launch {
            _spinner.value = true
            if (!firestoreRepo.saveDataInFirestore(uuid)) {
                _errorMessage.value = "Cannot export data"
            }
            _spinner.value = false
        }
    }

    fun getDataFromFirestore(uuid: String) {
        _spinner.value = true
        viewModelScope.launch {
            if (!firestoreRepo.getDataFromFirestore(uuid)) {
                _errorMessage.value = "Import ID does not exist."
            }
        }
        _spinner.value = false
    }

    // called after the UI shows snackbar
    fun onSnackbarDisplayed() {
        _errorMessage.value = null
    }
}