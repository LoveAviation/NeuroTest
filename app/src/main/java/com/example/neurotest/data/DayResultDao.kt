package com.example.neurotest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResult(testResult: DayResult)

    @Query("SELECT * FROM stats WHERE date = :date")
    suspend fun findTestResultByDate(date: String): DayResult?

    @Query("SELECT * FROM stats ORDER BY date DESC LIMIT 7")
    suspend fun findLastSevenTestResults(): List<DayResult>

    @Query("UPDATE stats SET attentionTest = :newAttentionTest WHERE date = :date")
    suspend fun updateAttentionTest(date: String, newAttentionTest: String)

    @Query("UPDATE stats SET memoryTest = :newMemoryTest WHERE date = :date")
    suspend fun updateMemoryTest(date: String, newMemoryTest: String)

    @Query("UPDATE stats SET logicTest = :newLogicTest WHERE date = :date")
    suspend fun updateLogicTest(date: String, newLogicTest: String)

    @Query("UPDATE stats SET reactionTest = :newReactionTest WHERE date = :date")
    suspend fun updateReactionTest(date: String, newReactionTest: String)

    @Query("UPDATE stats SET mathTest = :newMathTest WHERE date = :date")
    suspend fun updateMathTest(date: String, newMathTest: String)

    @Query("UPDATE stats SET speechTest = :newSpeechTest WHERE date = :date")
    suspend fun updateSpeechTest(date: String, newSpeechTest: String)

    @Query("DELETE FROM stats")
    suspend fun deleteAllStats()
}