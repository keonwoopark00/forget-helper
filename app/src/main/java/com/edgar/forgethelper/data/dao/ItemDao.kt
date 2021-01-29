package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.edgar.forgethelper.data.model.MItem
import kotlinx.coroutines.flow.Flow

// ItemDao.kt

@Dao
interface ItemDao : BaseDao<MItem> {

    // SELECT all items related to a section id from the database
    @Query("SELECT * FROM tb_item WHERE section_id = :sectionId")
    suspend fun selectItemsBySectionId(sectionId: Long): Flow<List<MItem>>

    // SELECT items according to name
    @Query("SELECT * FROM tb_item WHERE name LIKE '%:name%'")
    suspend fun selectItemsByName(name: String): List<MItem>
}