package com.edgar.forgethelper.data.repository

import com.edgar.forgethelper.data.dao.ItemDao
import com.edgar.forgethelper.data.model.MItem
import kotlinx.coroutines.flow.Flow


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

    suspend fun getItemsBySectionId(sectionId: Long): Flow<List<MItem>> {
        return itemDao.selectItemsBySectionId(sectionId)
    }

    suspend fun insertItem(item: MItem) {
        itemDao.insert(item)
    }

    suspend fun deleteItem(item: MItem) {
        itemDao.delete(item)
    }

    suspend fun getItemsByName(name: String): List<MItem> {
        return itemDao.selectItemsByName(name)
    }
}