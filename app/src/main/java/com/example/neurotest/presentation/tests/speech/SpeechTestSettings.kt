package com.example.neurotest.presentation.tests.speech

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivitySpeechTestSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpeechTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivitySpeechTestSettingsBinding

    private var wordsInTest = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeechTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()

        binding.speechTestBackButton.setOnClickListener{ finish() }

        binding.startButtonSpts.setOnClickListener{
            val intent = Intent(this, SpeechTest::class.java)
            intent.putExtra(SPEECH_TEST_KEY, wordsInTest)
            startActivity(intent)
        }

        binding.wordsCountSeekbarSpts.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress) {
                    0 -> binding.seekbarTextSpts.text = "4"
                    1 -> binding.seekbarTextSpts.text = "6"
                    2 -> binding.seekbarTextSpts.text = "8"
                    3 -> binding.seekbarTextSpts.text = "10"
                }
                wordsInTest = (progress*2)+4
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })
    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (!grantResults.isNotEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.allow_access_to_the_microphone), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object{
        const val SPEECH_TEST_KEY = "number_of_words"
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
    }
}