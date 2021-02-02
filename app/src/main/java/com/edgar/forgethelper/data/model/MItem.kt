package com.edgar.forgethelper.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

// MItem.kt

@Parcelize
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
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "createdDate")
    var createdDate: String = Date().toString(),

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "location_id")
    var location_id: Long = -1,

    @ColumnInfo(name = "location_name")
    var location_name: String = "",

    @ColumnInfo(name = "section_id")
    var section_id: Long = -1,

    @ColumnInfo(name = "section_name")
    var section_name: String = ""
) : Parcelable
