package com.edgar.forgethelper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.data.repository.DataHandleError
import com.edgar.forgethelper.data.repository.DataRepository
import com.edgar.forgethelper.data.repository.ItemRepository
import kotlinx.coroutines.launch

// ItemViewModel.kt

class ItemViewModel(application: Application) :
    AndroidViewModel(application) {

    companion object {
        class Factory(
            val application: Application,
        ) :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ItemViewModel(application) as T
            }
        }
    }

    // error message when error happens
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    /**
     * ItemRepository-related objects
     */
    private val repository: DataRepository = DataRepository(application)
    private val itemRepo: ItemRepository
        get() = repository.getItemRepo()

    fun getItemsBySectionId(sectionId: Long): LiveData<List<MItem>> {
        return itemRepo.getItemsBySectionId(sectionId)
    }

    fun insertItem(item: MItem) = launchDataHandler {
        itemRepo.insertItem(item)
    }

    fun deleteItem(item: MItem) = launchDataHandler {
        itemRepo.deleteItem(item)
    }

    fun getSearchResult(searchString: String): LiveData<List<MItem>> {
        return itemRepo.getItemsByName(searchString)
    }

    fun getItemDetail(itemId: Long): LiveData<MItem?> {
        return itemRepo.getItemById(itemId)
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