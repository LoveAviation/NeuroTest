package com.example.neurotest.presentation.tests.memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivityMemoryTestBinding
import com.example.neurotest.presentation.StatsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MemoryTest : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTestBinding

    private val viewModel: StatsVM by viewModels()

    private val handler = Handler(Looper.getMainLooper())

    private var testLength = 5

    private var inputList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testLength = intent.getIntExtra(MemoryTestSettings.MEMORY_TEST_LENGTH_KEY, 5)

        var numbers = IntArray(testLength) { (1..9).random() }

        binding.startTestMt.setOnClickListener{
            startTest(numbers)
        }

        val buttons = listOf(
            binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        // Устанавливаем обработчик для каждой кнопки
        buttons.forEach { button ->
            button.setOnClickListener {
                val number = (it as Button).text.toString().toInt()

                inputList.add(number)
                binding.mainTextMt.text = inputList.joinToString(" ")

                if (inputList.size == testLength){
                    viewModel.addOrUpdateMemoryResult("${compareResults(numbers, inputList)}/${testLength}")
                    congratsWindow(compareResults(numbers, inputList), testLength, numbers)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun startTest(chain: IntArray){
        binding.startTestMt.visibility = View.GONE
        lifecycleScope.launch{
            for (i in 0 until chain.size){
                handler.post{ binding.mainTextMt.text = chain[i].toString() }
                delay(1200)
                handler.post{ binding.mainTextMt.text = "" }
                delay(500)
            }
            handler.post{
                binding.findTextMt.text = getString(R.string.try_to_remember_the_numbers)
                binding.numberPadMt.visibility = View.VISIBLE
            }
        }
    }

    private fun congratsWindow(correctAnswers: Int, all: Int, numbers: IntArray){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.congratulation))
        builder.setMessage("You memorized ${correctAnswers}/${all} numbers correctly. Correct numbers were: ${numbers.joinToString(" ")}")
        builder.setPositiveButton("ОК") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.show()
    }

    fun compareResults(intArray: IntArray, mutableList: MutableList<Int>): Int {
        var commonCount = 0

        for (i in 0 until testLength) {
            if (intArray[i] == mutableList[i]) {
                commonCount++
            }
        }
        return commonCount
    }

}