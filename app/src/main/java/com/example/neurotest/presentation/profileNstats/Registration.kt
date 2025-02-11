package com.example.neurotest.presentation.profileNstats

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.databinding.ActivityRegistrationBinding
import com.google.android.material.snackbar.Snackbar

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    private lateinit var strangeSharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        strangeSharedPref = getSharedPreferences(ProfileNStats.Companion.PROFILE_KEY, MODE_PRIVATE)

        val name = strangeSharedPref.getString(ProfileNStats.Companion.NAME_KEY, null)
        val surname = strangeSharedPref.getString(ProfileNStats.Companion.SURNAME_KEY, null)
        val address = strangeSharedPref.getString(ProfileNStats.Companion.ADDRESS_KEY, null)
        val extra = strangeSharedPref.getString(ProfileNStats.Companion.EXTRA_KEY, null)

        if(name != null || surname != null || address != null){
            binding.nameET.setText(name)
            binding.surnameET.setText(surname)
            binding.addressET.setText(address)
            binding.extraET.setText(extra)
        }


        binding.registrationBackButton.setOnClickListener{
            if(name != null || surname != null || address != null){
                startActivity(Intent(this, ProfileNStats::class.java))
            }
            finish()
        }

        binding.applyButton.setOnClickListener{
            if(binding.nameET.text?.trim().isNullOrEmpty() ||
                binding.surnameET.text?.trim().isNullOrEmpty() ||
                binding.addressET.text?.trim().isNullOrEmpty()){
                Snackbar.make(binding.root, "Name, surname or address is empty", Snackbar.LENGTH_SHORT).show()
            }else {
                val name = binding.nameET.text?.trim().toString()
                val surname = binding.surnameET.text?.trim().toString()
                val address = binding.addressET.text?.trim().toString()
                var extra: String? = null
                if (!binding.extraET.text?.trim().isNullOrEmpty()) {
                    extra = binding.extraET.text?.trim().toString()
                }
                editor = strangeSharedPref.edit()
                editor.putString(ProfileNStats.Companion.NAME_KEY, name)
                editor.putString(ProfileNStats.Companion.SURNAME_KEY, surname)
                editor.putString(ProfileNStats.Companion.ADDRESS_KEY, address)
                editor.putString(ProfileNStats.Companion.EXTRA_KEY, extra)
                editor.apply()
                startActivity(Intent(this, ProfileNStats::class.java))
                finish()
            }
        }

    }
}