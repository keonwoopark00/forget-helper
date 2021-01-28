package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

// MItem.kt

@Entity(tableName = "item")
data class MItem(
    // item name
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1,

    @ColumnInfo(name = "section_id")
    var section_id: Long = -1
) : BaseEntity()
