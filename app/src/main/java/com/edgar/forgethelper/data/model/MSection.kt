package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// MSection.kt

@Entity(
    tableName = "tb_section",
    foreignKeys = [ForeignKey(
        entity = MLocation::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("location_id")
    )]
)
data class MSection(
    // section name
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1
) : BaseEntity()
