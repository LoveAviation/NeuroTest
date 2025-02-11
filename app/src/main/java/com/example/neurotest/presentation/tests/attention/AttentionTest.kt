package com.example.neurotest.presentation.tests.attention

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityAttentionTestBinding
import com.example.neurotest.R
import com.example.neurotest.presentation.StatsVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
@SuppressLint("DefaultLocale")
class AttentionTest : AppCompatActivity() {
    private lateinit var binding : ActivityAttentionTestBinding

    private val viewModel: StatsVM by viewModels()

    private var correctLetter = "F"   //это ищем
    private var incorrectLetter = "E" //этим заполняем

    private var secondsElapsed = 0 // Переменная для отсчета времени
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            // Увеличиваем счетчик времени
            secondsElapsed++
            updateTimerText()
            // Вызываем себя снова через 1 секунду
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttentionTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val width = intent.getIntExtra(AttentionTestSettings.ATTENTION_TEST_WIDTH_KEY,5)
        val height = intent.getIntExtra(AttentionTestSettings.ATTENTION_TEST_HEIGHT_KEY,5)

        when(Random.nextInt(0,5)){
            0 ->{
                correctLetter = "F"
                incorrectLetter = "E"
            }
            1 ->{
                correctLetter = "P"
                incorrectLetter = "R"
            }
            2 ->{
                correctLetter = "C"
                incorrectLetter = "G"
            }
            3->{
                correctLetter = "i"
                incorrectLetter = "j"
            }
            4->{
                correctLetter = "O"
                incorrectLetter = "Q"
            }
        }
        binding.letterAt.text = correctLetter

        val letters = MutableList(width*height) { incorrectLetter }
        letters[Random.nextInt(width*height)] = correctLetter // Одна неправильная буква
        binding.mainFieldAt.numColumns = width

        binding.mainFieldAt.adapter = object : ArrayAdapter<String>(
            this,
            R.layout.attention_test_box,
            letters.map { it.toString() }
        ){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(R.id.gridItemText)
                textView.text = getItem(position) // Устанавливаем текст
                return view
            }
        }

        binding.mainFieldAt.setOnItemClickListener { _, _, position, _ ->
            if (letters[position] == correctLetter) {
                handler.removeCallbacks(runnable)
                val time = String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60)
                viewModel.addOrUpdateAttentionResult("Field: ${width}x${height}; Time: $time")
                congratsWindow()
            } else {
                Snackbar.make(binding.root, getString(R.string.no_not_this_letter_try_again), Snackbar.LENGTH_LONG).show()
            }
        }
        startStopwatch()
    }

    private fun updateTimerText() {
        binding.timeAt.text = String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60)
    }

    private fun startStopwatch() {
        // Запускаем Runnable для отсчета времени
        handler.post(runnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable) // Останавливаем секундомер, если активность уходит в фоновый режим
    }

    override fun onResume() {
        super.onResume()
        // Возобновляем секундомер, если активность возвращается
        if (secondsElapsed > 0) {
            startStopwatch()
        }
    }

    private fun congratsWindow(){
        val timeFormatted = String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.congratulation))
        builder.setMessage(getString(R.string.you_have_founded_in_just, correctLetter, timeFormatted))
        // Кнопка ОК для закрытия диалога
        builder.setPositiveButton("ОК") { dialog, _ ->
            dialog.dismiss() // Закрываем диалог при нажатии на кнопку
            finish()
        }
        // Показываем диалог
        builder.show()
    }


}