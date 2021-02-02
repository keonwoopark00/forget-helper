package com.edgar.forgethelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.edgar.forgethelper.data.model.MItem

// ItemDao.kt

@Dao
interface ItemDao : BaseDao<MItem> {

    // SELECT all items related to a section id from the database
    @Query("SELECT * FROM tb_item WHERE section_id = :sectionId")
    fun selectItemsBySectionId(sectionId: Long): LiveData<List<MItem>>

    // SELECT an item by item id
    @Query("SELECT * FROM tb_item WHERE id = :itemId")
    fun selectItemById(itemId: Long): LiveData<MItem?>

   // SELECT items according to name
    @Query("SELECT * FROM tb_item WHERE name LIKE '%' + :n + '%'")
    suspend fun selectItemsByName(n: String): List<MItem>
}