package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.data.model.SectionWithItems
import kotlinx.coroutines.flow.Flow

// SectionDao.kt

@Dao
interface SectionDao : BaseDao<MSection> {

    // SELECT all sections related to a location id from the database
    @Query("SELECT * FROM tb_section WHERE location_id = :locationId")
    suspend fun selectSectionsByLocationId(locationId: Long): Flow<List<MSection>>

    // JOIN SELECT: select all items related to a section
    @Transaction
    @Query("SELECT * FROM tb_section")
    suspend fun selectSectionWithItems(): List<SectionWithItems>
}