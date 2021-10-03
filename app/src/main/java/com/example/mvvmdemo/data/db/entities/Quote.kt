package com.example.mvvmdemo.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val author: String? = null,
    val quoteText: String? = null
    )