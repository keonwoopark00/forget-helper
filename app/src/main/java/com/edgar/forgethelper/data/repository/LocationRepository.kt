package com.edgar.forgethelper.data.repository

import com.edgar.forgethelper.data.dao.LocationDao
import com.edgar.forgethelper.data.model.LocationWithSections
import com.edgar.forgethelper.data.model.MLocation
import kotlinx.coroutines.flow.Flow


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

    suspend fun getAllLocations(): Flow<List<MLocation>> {
        return locationDao.selectAllLocations()
    }

    suspend fun insertLocation(location: MLocation) {
        locationDao.insert(location)
    }

    suspend fun deleteLocation(location: MLocation) {
        locationDao.delete(location)
    }

    suspend fun getLocationWithSections(): List<LocationWithSections> {
        return locationDao.selectLocationWithSections()
    }
}