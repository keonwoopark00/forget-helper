package com.edgar.forgethelper.data.repository

import android.app.Application
import com.edgar.forgethelper.data.AppDatabase


// DataRepository.kt

class DataRepository(application: Application) {
    val database: AppDatabase = AppDatabase.getDatabase(application)

    fun getLocationRepo() = LocationRepository.getInstance(this)
    fun getSectionRepo() = SectionRepository.getInstance(this)
    fun getItemRepo() = ItemRepository.getInstance(this)
    fun getFirestoreRepo() = FirestoreRepository.getInstance(this)
}

/**
 * Thrown where there was a error in processing database request
 */
class DataHandleError(message: String, cause: Throwable) : Throwable(message, cause)