package com.example.neurotest.presentation.tests.memory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityMemoryTestSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoryTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTestSettingsBinding

    private var testLength = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memoryTestBackButton.setOnClickListener{ finish() }

        binding.lengthSeekbarS.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> binding.seekbarTextS.text = "5"
                    1 -> binding.seekbarTextS.text = "10"
                    2 -> binding.seekbarTextS.text = "15"
                    3 -> binding.seekbarTextS.text = "20"
                }
                testLength = (progress+1)*5
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        binding.startButtonS.setOnClickListener{
            val intent = Intent(this, MemoryTest::class.java)
            intent.putExtra(MEMORY_TEST_LENGTH_KEY, testLength)
            startActivity(intent)
        }
    }

    companion object{
        const val MEMORY_TEST_LENGTH_KEY = "test length"
    }
}