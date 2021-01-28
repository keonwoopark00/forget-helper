package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

// MSection.kt

@Entity(tableName = "section")
data class MSection(
    // section name
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1
) : BaseEntity()
