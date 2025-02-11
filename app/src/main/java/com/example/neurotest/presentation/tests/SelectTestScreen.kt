package com.example.neurotest.presentation.tests

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivitySelectTestScreenBinding
import com.example.neurotest.presentation.tests.attention.AttentionTestSettings
import com.example.neurotest.presentation.tests.logic.LogicTestSettings
import com.example.neurotest.presentation.tests.math.MathTestSettings
import com.example.neurotest.presentation.tests.memory.MemoryTestSettings
import com.example.neurotest.presentation.tests.reaction.ReactionTestSettings
import com.example.neurotest.presentation.tests.speech.SpeechTestSettings

class SelectTestScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySelectTestScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTestScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testsBackButton.setOnClickListener{ finish() }

        binding.attentionTestSts.setOnClickListener{
            startActivity(Intent(this, AttentionTestSettings::class.java))
        }

        binding.memoryTestSts.setOnClickListener{
            startActivity(Intent(this, MemoryTestSettings::class.java))
        }

        binding.logicTestSts.setOnClickListener{
            startActivity(Intent(this, LogicTestSettings::class.java))
        }

        binding.reactionTestSts.setOnClickListener{
            startActivity(Intent(this, ReactionTestSettings::class.java))
        }

        binding.mathTestSts.setOnClickListener{
            startActivity(Intent(this, MathTestSettings::class.java))
        }

        binding.speechTestSts.setOnClickListener{
            startActivity(Intent(this, SpeechTestSettings::class.java))
        }
    }
}