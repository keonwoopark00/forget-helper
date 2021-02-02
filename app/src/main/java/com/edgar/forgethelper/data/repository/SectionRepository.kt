package com.edgar.forgethelper.data.repository

import androidx.lifecycle.LiveData
import com.edgar.forgethelper.data.dao.SectionDao
import com.edgar.forgethelper.data.model.MSection


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

    fun getSectionsByLocationId(locationId: Long): LiveData<List<MSection>> {
        return sectionDao.selectSectionsByLocationId(locationId)
    }

    suspend fun insertSection(section: MSection) {
        sectionDao.insert(section)
    }

    suspend fun deleteSection(section: MSection) {
        sectionDao.delete(section)
    }
}