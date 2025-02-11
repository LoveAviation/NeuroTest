package com.example.neurotest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DayResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testResultDao(): DayResultDao
}