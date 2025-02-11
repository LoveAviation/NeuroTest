package com.example.neurotest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neurotest.data.DayResult
import com.example.neurotest.data.DayResultDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatsVM @Inject constructor(
    private val testResultDao: DayResultDao
) : ViewModel() {

    private var _favForms = MutableLiveData<List<DayResult>?>(null)
    val favForms: LiveData<List<DayResult>?> get() = _favForms

    fun getLastSevenDates(){
        viewModelScope.launch{
            _favForms.value = testResultDao.findLastSevenTestResults()
        }
    }

    suspend fun isTestResultExist(currentDate: String): Boolean {
        val existingResult = testResultDao.findTestResultByDate(currentDate)
        return existingResult != null
    }

    fun addOrUpdateAttentionResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)
            // Получаем текущую дату

            if (!exists) {
                // Если записи нет, создаём новую строку
                val newTestResult = DayResult(
                    date = currentDate,
                    attentionTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                // Если запись существует, обновляем attentionResult
                testResultDao.updateAttentionTest(currentDate, result)
            }
        }
    }

    fun addOrUpdateSpeechResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)

            if (!exists) {
                val newTestResult = DayResult(
                    date = currentDate,
                    speechTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                testResultDao.updateSpeechTest(currentDate, result)
            }
        }
    }

    fun addOrUpdateMemoryResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)

            if (!exists) {
                val newTestResult = DayResult(
                    date = currentDate,
                    memoryTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                testResultDao.updateMemoryTest(currentDate, result)
            }
        }
    }

    fun addOrUpdateLogicResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)

            if (!exists) {
                val newTestResult = DayResult(
                    date = currentDate,
                    logicTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                testResultDao.updateLogicTest(currentDate, result)
            }
        }
    }

    fun addOrUpdateReactionResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)

            if (!exists) {
                val newTestResult = DayResult(
                    date = currentDate,
                    reactionTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                testResultDao.updateReactionTest(currentDate, result)
            }
        }
    }

    fun addOrUpdateMathResult(result: String) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val exists = isTestResultExist(currentDate)

            if (!exists) {
                val newTestResult = DayResult(
                    date = currentDate,
                    mathTest = result
                )
                testResultDao.insertTestResult(newTestResult)
            } else {
                testResultDao.updateMathTest(currentDate, result)
            }
        }
    }

    fun deleteStats(){
        viewModelScope.launch{
            testResultDao.deleteAllStats()
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance() // Получает текущую дату и время
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Форматирует дату
        return formatter.format(calendar.time)
    }
}