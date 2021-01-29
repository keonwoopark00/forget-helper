package com.edgar.forgethelper.data.repository

import com.edgar.forgethelper.data.dao.SectionDao
import com.edgar.forgethelper.data.model.MSection
import com.edgar.forgethelper.data.model.SectionWithItems
import kotlinx.coroutines.flow.Flow


// SectionRepository.kt

class SectionRepository(
    private val dataRepository: DataRepository
) {
    companion object {
        @Volatile
        private var INSTANCE: SectionRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): SectionRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SectionRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val sectionDao: SectionDao
        get() = dataRepository.database.sectionDao()

    suspend fun getSectionsByLocationId(locationId: Long): Flow<List<MSection>> {
        return sectionDao.selectSectionsByLocationId(locationId)
    }

    suspend fun insertSection(section: MSection) {
        sectionDao.insert(section)
    }

    suspend fun deleteSection(section: MSection) {
        sectionDao.delete(section)
    }

    suspend fun getSectionWithItems(): List<SectionWithItems> {
        return sectionDao.selectSectionWithItems()
    }
}