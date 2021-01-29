package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.edgar.forgethelper.data.model.LocationWithSections
import com.edgar.forgethelper.data.model.MLocation
import kotlinx.coroutines.flow.Flow

// LocationDao.kt

@Dao
interface LocationDao : BaseDao<MLocation> {

    // SELECT all locations from the database
    @Query("SELECT * FROM tb_location")
    suspend fun selectAllLocations(): Flow<List<MLocation>>

    // JOIN SELECT: select all sections related to a location
    @Transaction
    @Query("SELECT * FROM tb_location")
    suspend fun selectLocationWithSections(): List<LocationWithSections>
}