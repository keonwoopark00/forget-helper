package com.edgar.forgethelper.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

// BaseDao.kt

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    @Delete
    suspend fun delete(vararg obj: T)
}