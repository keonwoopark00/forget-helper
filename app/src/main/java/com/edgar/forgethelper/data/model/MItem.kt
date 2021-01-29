package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// MItem.kt

@Entity(
    tableName = "tb_item",
    foreignKeys = [
        ForeignKey(
            entity = MLocation::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("location_id")
        ), ForeignKey(
            entity = MSection::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("section_id")
        )]
)
data class MItem(
    // item name
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1,

    @ColumnInfo(name = "section_id")
    var section_id: Long = -1
) : BaseEntity()
