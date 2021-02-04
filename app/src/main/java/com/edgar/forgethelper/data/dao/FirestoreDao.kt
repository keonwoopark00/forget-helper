package com.edgar.forgethelper.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.data.model.MSection

@Dao
interface FirestoreDao {

    @Query("SELECT * FROM tb_location")
    suspend fun selectAllLocations(): List<MLocation>

    @Query("SELECT * FROM tb_section")
    suspend fun selectAllSections(): List<MSection>

    @Query("SELECT * FROM tb_item")
    suspend fun selectAllItems(): List<MItem>

    @Query("DELETE FROM tb_location")
    suspend fun deleteAllLocations()

    @Query("DELETE FROM tb_section")
    suspend fun deleteAllSections()

    @Query("DELETE FROM tb_item")
    suspend fun deleteAllItems()

    @Insert
    suspend fun insertLocation(location: MLocation)

    @Insert
    suspend fun insertSection(section: MSection)

    @Insert
    suspend fun insertItem(item: MItem)
}