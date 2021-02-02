package com.edgar.forgethelper.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.edgar.forgethelper.data.dao.ItemDao
import com.edgar.forgethelper.data.dao.LocationDao
import com.edgar.forgethelper.data.dao.SectionDao
import com.edgar.forgethelper.data.model.MItem
import com.edgar.forgethelper.data.model.MLocation
import com.edgar.forgethelper.data.model.MSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// AppDatabase.kt

@Database(
    version = 4, exportSchema = false,
    entities = [
        MLocation::class,
        MSection::class,
        MItem::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    // Daos
    abstract fun locationDao(): LocationDao
    abstract fun sectionDao(): SectionDao
    abstract fun itemDao(): ItemDao

    companion object {
        private const val DATABASE_NAME = "forget-helper.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}