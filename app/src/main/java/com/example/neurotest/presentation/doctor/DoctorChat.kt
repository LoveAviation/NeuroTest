package com.example.neurotest.presentation.doctor

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivityDoctorChatBinding
import com.example.neurotest.presentation.profileNstats.ProfileNStats
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DoctorChat : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorChatBinding

    private val handler = Handler(Looper.getMainLooper())

    private val listOfNames = listOf("Dr. Samuel Harrington","Dr. William Lancaster","Dr. Jonathan Mercer","Dr. Thomas Caldwell","Dr. Nathaniel Benson")

    private lateinit var strangerSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        strangerSharedPref = getSharedPreferences(ProfileNStats.Companion.PROFILE_KEY, MODE_PRIVATE)

        Glide.with(binding.root)
            .load(R.drawable.doctor_avatar)
            .circleCrop()
            .into(binding.doctorAvatarDc)

        binding.h1textDc.text = listOfNames.random()

        binding.chatBackButton.setOnClickListener{
            finish()
        }

        binding.sendStats.setOnClickListener{
            val name = strangerSharedPref.getString(ProfileNStats.Companion.NAME_KEY, null)
            val surname = strangerSharedPref.getString(ProfileNStats.Companion.SURNAME_KEY, null)
            binding.sendStats.visibility = View.GONE
            binding.patientName.text = getString(R.string.hello_i_m_please_check_my_stats, surname, name)
            binding.secondMessage.visibility = View.VISIBLE
        }

        lifecycleScope.launch{
            delay(1100)
            handler.post{
                binding.doctorAvatarDc.visibility = View.VISIBLE
                binding.typingTextDc.visibility = View.VISIBLE
            }
            delay(1100)
            handler.post{
                binding.firstMessageDc.visibility = View.VISIBLE
                binding.typingTextDc.visibility = View.GONE
            }
            delay(600)
            handler.post{
                binding.sendStats.visibility = View.VISIBLE
            }
        }
    }
}