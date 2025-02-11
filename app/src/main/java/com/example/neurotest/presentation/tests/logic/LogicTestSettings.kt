package com.example.neurotest.presentation.tests.logic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityLogicTestSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogicTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivityLogicTestSettingsBinding

    private var numberQuestions = 4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogicTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logicTestBackButton.setOnClickListener{ finish() }

        binding.questionsSeekbarLts.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> binding.seekbarTextLts.text = "4"
                    1 -> binding.seekbarTextLts.text = "6"
                    2 -> binding.seekbarTextLts.text = "8"
                    3 -> binding.seekbarTextLts.text = "10"
                }
                numberQuestions = (progress*2)+4
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        binding.startButtonLts.setOnClickListener{
            val intent = Intent(this, LogicTest::class.java)
            intent.putExtra(LOGIC_TEST_QUESTIONS_KEY, numberQuestions)
            startActivity(intent)
        }
    }

    companion object{
        const val LOGIC_TEST_QUESTIONS_KEY = "number of questions"
    }
}