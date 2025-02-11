package com.example.neurotest.presentation.tests.math

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityMathTestSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MathTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivityMathTestSettingsBinding

    private var numberOfExamples = 5;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mathTestBackButton.setOnClickListener{ finish() }

        binding.numberOfExamplesSeekbarMts.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> binding.numberOfExamplesTextMts.text = "5"
                    1 -> binding.numberOfExamplesTextMts.text = "10"
                    2 -> binding.numberOfExamplesTextMts.text = "15"
                    3 -> binding.numberOfExamplesTextMts.text = "20"
                }
                numberOfExamples = (progress+1)*5
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        binding.startButtonMts.setOnClickListener{
            val intent = Intent(this, MathTest::class.java)
            intent.putExtra(MATH_TEST_EXAMPLES_KEY, numberOfExamples)
            startActivity(intent)
        }
    }

    companion object{
        const val MATH_TEST_EXAMPLES_KEY = "number of questions"
    }
}