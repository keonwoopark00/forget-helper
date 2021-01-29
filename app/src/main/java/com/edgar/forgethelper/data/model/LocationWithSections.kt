package com.edgar.forgethelper.data.model

import androidx.room.Embedded
import androidx.room.Relation

// LocationAndAllSections.kt

class LocationWithSections {
    @Embedded
    var location: MLocation? = null

    @Relation(parentColumn = "id", entityColumn = "location_id", entity =  MSection::class)
    var sections: List<MSection> = mutableListOf()
}