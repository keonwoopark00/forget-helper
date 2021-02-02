package com.edgar.forgethelper.data.repository

import androidx.lifecycle.LiveData
import com.edgar.forgethelper.data.dao.ItemDao
import com.edgar.forgethelper.data.model.MItem


// ItemRepository.kt

class ItemRepository(
    private val dataRepository: DataRepository
) {
    companion object {
        @Volatile
        private var INSTANCE: ItemRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): ItemRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ItemRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val itemDao: ItemDao
        get() = dataRepository.database.itemDao()

    fun getItemsBySectionId(sectionId: Long): LiveData<List<MItem>> {
        return itemDao.selectItemsBySectionId(sectionId)
    }

    suspend fun insertItem(item: MItem) {
        itemDao.insert(item)
    }

    suspend fun deleteItem(item: MItem) {
        itemDao.delete(item)
    }

    fun getItemsByName(name: String): LiveData<List<MItem>> {
        return itemDao.selectItemsByName(name)
    }

    fun getItemById(itemId: Long): LiveData<MItem?> {
        return itemDao.selectItemById(itemId)
    }
}