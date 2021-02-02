package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.edgar.forgethelper.data.model.MLocation

// LocationDao.kt

@Dao
interface LocationDao : BaseDao<MLocation> {

    // SELECT all locations from the database
    @get:Query("SELECT * FROM tb_location ORDER BY createdDate")
    val locationsLiveData: LiveData<List<MLocation>>

}