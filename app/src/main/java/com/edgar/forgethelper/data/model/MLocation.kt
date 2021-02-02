package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// MLocation.kt

@Entity(tableName = "tb_location")
data class MLocation(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "createdDate")
    var createdDate: String = Date().toString(),

    @ColumnInfo(name = "name")
    var name: String = ""
)
