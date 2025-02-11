package com.example.neurotest.presentation.tests.attention

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityAttentionTestSettingsBinding

class AttentionTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivityAttentionTestSettingsBinding

    private var width = 2
    private var height = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttentionTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.attentionTestBackButton.setOnClickListener{
            finish()
        }

        binding.widthSeekbarAts.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> binding.widthTextAts.text = "5"
                    1 -> binding.widthTextAts.text = "6"
                    2 -> binding.widthTextAts.text = "7"
                    3 -> binding.widthTextAts.text = "8"
                }
                width = progress+5
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        binding.heightSeekbarAts.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> binding.heightTextAts.text = "5"
                    1 -> binding.heightTextAts.text = "10"
                    2 -> binding.heightTextAts.text = "15"
                    3 -> binding.heightTextAts.text = "20"
                }
                height = (progress+1)*5
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })

        binding.startButtonAts.setOnClickListener{
            val intent = Intent(this, AttentionTest::class.java)
            intent.putExtra(ATTENTION_TEST_WIDTH_KEY, width)
            intent.putExtra(ATTENTION_TEST_HEIGHT_KEY, height)
            startActivity(intent)
        }
    }

    companion object{
        const val ATTENTION_TEST_WIDTH_KEY = "width_key"
        const val ATTENTION_TEST_HEIGHT_KEY = "height_key"
    }
}