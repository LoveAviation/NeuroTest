package com.example.neurotest.presentation.tests.reaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityReactionTestSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReactionTestSettings : AppCompatActivity() {
    private lateinit var binding: ActivityReactionTestSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReactionTestSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButtonRts.setOnClickListener{
            startActivity(Intent(this, ReactionTest::class.java))
        }

        binding.reactionTestBackButton.setOnClickListener{
            finish()
        }
    }
}