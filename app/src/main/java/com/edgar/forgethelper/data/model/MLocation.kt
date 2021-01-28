package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

// MLocation.kt

@Entity(tableName = "location")
data class MLocation(
    // location name
    @ColumnInfo(name = "name")
    var name: String = ""
) : BaseEntity()
