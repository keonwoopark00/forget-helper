package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.edgar.forgethelper.data.model.MSection

// SectionDao.kt

@Dao
interface SectionDao : BaseDao<MSection> {

    // SELECT all sections related to a location id from the database
    @Query("SELECT * FROM tb_section WHERE location_id = :locationId" )
    fun selectSectionsByLocationId(locationId: Long): LiveData<List<MSection>>

}