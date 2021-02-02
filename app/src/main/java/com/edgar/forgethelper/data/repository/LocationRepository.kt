package com.edgar.forgethelper.data.repository

import androidx.lifecycle.LiveData
import com.edgar.forgethelper.data.dao.LocationDao
import com.edgar.forgethelper.data.model.MLocation


// LocationRepository.kt

class LocationRepository(
    private val dataRepository: DataRepository
) {
    companion object {
        @Volatile
        private var INSTANCE: LocationRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): LocationRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val locationDao: LocationDao
        get() = dataRepository.database.locationDao()

    val locations: LiveData<List<MLocation>> = locationDao.locationsLiveData

    suspend fun insertLocation(location: MLocation) {
        locationDao.insert(location)
    }

    suspend fun deleteLocation(location: MLocation) {
        locationDao.delete(location)
    }

}