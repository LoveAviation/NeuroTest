package com.example.neurotest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats")
data class DayResult(
    @PrimaryKey val date: String,
    val attentionTest: String = "",
    val memoryTest: String = "",
    val logicTest: String = "",
    val reactionTest: String = "",
    val mathTest: String = "",
    val speechTest: String = ""
)

