package com.example.neurotest.presentation.tests.reaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivityReactionTestBinding
import com.example.neurotest.presentation.StatsVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@SuppressLint("DefaultLocale")
@AndroidEntryPoint
class ReactionTest : AppCompatActivity() {
    private lateinit var binding: ActivityReactionTestBinding

    private val viewModel: StatsVM by viewModels()

    private var millisElapsed = 0 // Переменная для отсчета времени
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            millisElapsed++
            updateTimer()
            handler.postDelayed(this, 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReactionTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRt.setOnClickListener{
            binding.buttonRt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
            binding.buttonRt.text = ""
            startTest()
        }

    }

    private fun updateTimer(){
        binding.reactionTimeRt.text = String.format("%02d.%02d", millisElapsed / 100, millisElapsed % 100)
    }

    private fun startTest(){
        val randomTime : Long = (((20..50).random())*100).toLong()
        binding.buttonRt.setOnClickListener{ Snackbar.make(binding.root, getString(R.string.it_s_early_wait_for_the_button_to_change_color), Snackbar.LENGTH_SHORT).show() }

        lifecycleScope.launch{
            delay(randomTime)
            handler.post{
                binding.buttonRt.backgroundTintList = ContextCompat.getColorStateList(this@ReactionTest, R.color.red)
                binding.buttonRt.setOnClickListener{
                    stopTest()
                }
            }
            handler.post(runnable)
        }
    }

    fun stopTest(){
        handler.removeCallbacks(runnable)
        binding.buttonRt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)

        val timeFormatted = String.format("%02d.%02d", millisElapsed / 100, millisElapsed % 100)
        viewModel.addOrUpdateReactionResult(timeFormatted)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.congratulation))
        builder.setMessage("Your reaction time is $timeFormatted")
        // Кнопка ОК для закрытия диалога
        builder.setPositiveButton("ОК") { dialog, _ ->
            dialog.dismiss() // Закрываем диалог при нажатии на кнопку
            finish()
        }
        // Показываем диалог
        builder.show()
    }
}