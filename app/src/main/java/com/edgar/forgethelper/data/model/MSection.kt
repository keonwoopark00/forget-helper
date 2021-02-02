package com.edgar.forgethelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

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
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "createdDate")
    var createdDate: String = Date().toString(),

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1
)
