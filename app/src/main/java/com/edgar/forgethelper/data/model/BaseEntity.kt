package com.edgar.forgethelper.data.model

import androidx.room.PrimaryKey
import java.util.*

// BaseEntity.kt

open class BaseEntity {
    // PK
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var createdDate = Date()
}