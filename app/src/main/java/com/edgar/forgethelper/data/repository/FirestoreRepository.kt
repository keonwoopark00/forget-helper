package com.edgar.forgethelper.data.repository

import android.util.Log
import com.edgar.forgethelper.data.dao.FirestoreDao
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.data.model.MSection
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

// FirestoreRepository.kt

class FirestoreRepository(private val dataRepository: DataRepository) {
    companion object {
        @Volatile
        private var INSTANCE: FirestoreRepository? = null

        // singleton with synchronized
        fun getInstance(dataRepository: DataRepository): FirestoreRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FirestoreRepository(dataRepository).also { INSTANCE = it }
            }
        }
    }

    private val firestoreDao: FirestoreDao
        get() = dataRepository.database.firestoreDao()

    private suspend fun getLocations(): List<MLocation> {
        return firestoreDao.selectAllLocations()
    }

    private suspend fun getSections(): List<MSection> {
        return firestoreDao.selectAllSections()
    }

    private suspend fun getItems(): List<MItem> {
        return firestoreDao.selectAllItems()
    }

    private suspend fun deleteLocations() {
        return firestoreDao.deleteAllLocations()
    }

    private suspend fun deleteSections() {
        return firestoreDao.deleteAllSections()
    }

    private suspend fun deleteItems() {
        return firestoreDao.deleteAllItems()
    }

    private suspend fun insertLocation(location: MLocation) {
        return firestoreDao.insertLocation(location)
    }

    private suspend fun insertSection(section: MSection) {
        return firestoreDao.insertSection(section)
    }

    private suspend fun insertItem(item: MItem) {
        return firestoreDao.insertItem(item)
    }

    suspend fun saveDataInFirestore(uuid: String): Boolean{
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("users").document(uuid)
        var locs: List<MLocation>
        var secs: List<MSection>
        var items: List<MItem>
        withContext(Dispatchers.IO) {
            locs = getLocations()
            secs = getSections()
            items = getItems()
        }
        return try {
            // store locations
            locs.forEach {
                docRef.collection("locations")
                    .document(it.id.toString())
                    .set(it)
                    .await()
            }
            // store sections
            secs.forEach {
                docRef.collection("sections")
                    .document(it.id.toString())
                    .set(it)
                    .await()
            }
            // store items
            items.forEach {
                docRef.collection("items")
                    .document(it.id.toString())
                    .set(it)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getDataFromFirestore(uuid: String): Boolean {
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("users").document(uuid)
        val locs = ArrayList<MLocation>()
        val secs = ArrayList<MSection>()
        val items = ArrayList<MItem>()

        // if data with provided uuid exists
        if (docRef.collection("locations").get().await().documents.size > 0) {
            val locData = docRef.collection("locations").get().await().documents
            if (locData.size > 0) {
                locData.forEach {
                    val location = it.toObject(MLocation::class.java)
                    if (location != null) locs.add(location)
                }
            }
            val secData = docRef.collection("sections").get().await().documents
            if (secData.size > 0) {
                secData.forEach {
                    val section = it.toObject(MSection::class.java)
                    if (section != null) secs.add(section)
                }
            }
            val itemData = docRef.collection("items").get().await().documents
            if (itemData.size > 0) {
                itemData.forEach {
                    val item = it.toObject(MItem::class.java)
                    if (item != null) items.add(item)
                }
            }
        }
        // if data doesn't exist
        else {
            return false
        }
        withContext(Dispatchers.IO) {
            try {
                // delete from item table to keep foreign key constraints
                deleteItems()
                deleteSections()
                deleteLocations()
                if (locs.size > 0) locs.forEach { insertLocation(it) }
                if (secs.size > 0) secs.forEach { insertSection(it) }
                if (items.size > 0) items.forEach { insertItem(it) }
            } catch (e: Exception) {
                return@withContext
            }
        }
        return true
    }
}