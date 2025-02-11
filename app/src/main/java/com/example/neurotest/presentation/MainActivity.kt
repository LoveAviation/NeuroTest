package com.example.neurotest.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivityMainBinding
import com.example.neurotest.presentation.doctor.LoadingScreen
import com.example.neurotest.presentation.profileNstats.ProfileNStats
import com.example.neurotest.presentation.profileNstats.Registration
import com.example.neurotest.presentation.tests.SelectTestScreen
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var strangerSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.abc.setOnClickListener{
            startActivity(Intent(this, SelectTestScreen::class.java))
        }

        binding.profileButton.setOnClickListener{
            if(accountIs()) startActivity(Intent(this, ProfileNStats::class.java))
            else startActivity(Intent(this, Registration::class.java))
        }

        binding.consultationButton.setOnClickListener{
            if(accountIs()) startActivity(Intent(this, LoadingScreen::class.java))
            else Snackbar.make(binding.root, getString(R.string.you_must_be_registered_for_a_consultation), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun accountIs(): Boolean{
        strangerSharedPref = getSharedPreferences(ProfileNStats.Companion.PROFILE_KEY, MODE_PRIVATE)
        if(strangerSharedPref.getString(ProfileNStats.Companion.NAME_KEY, null) == null ||
            strangerSharedPref.getString(ProfileNStats.Companion.SURNAME_KEY, null) == null ||
            strangerSharedPref.getString(ProfileNStats.Companion.ADDRESS_KEY, null) == null){
            return false
        }else {
            return true
        }
    }
}