package com.edgar.forgethelper.data.model

import androidx.room.Embedded
import androidx.room.Relation

// SectionsAndAllItems.kt

class SectionWithItems {
    @Embedded
    var section: MSection? = null

    @Relation(parentColumn = "id", entityColumn = "section_id", entity =  MItem::class)
    var sections: List<MItem> = mutableListOf()
}