package com.example.neurotest.presentation.profileNstats

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivityProfileNstatsBinding
import com.example.neurotest.presentation.StatsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileNStats : AppCompatActivity() {

    private lateinit var binding: ActivityProfileNstatsBinding

    private val viewModel : StatsVM by viewModels()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var name: String? = null
    private var surname: String? = null
    private var address: String? = null
    private var extra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNstatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("AccountPref", MODE_PRIVATE)

        name = sharedPref.getString(NAME_KEY, null)
        surname = sharedPref.getString(SURNAME_KEY, null)
        address = sharedPref.getString(ADDRESS_KEY, null)
        extra = sharedPref.getString(EXTRA_KEY, null)

        binding.namePr.text = name
        binding.surnamePr.text = surname

        binding.profileBackButton.setOnClickListener{ finish() }

        binding.deleteProfilePr.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.deleting_profile))
            builder.setMessage(getString(R.string.do_you_really_want_to_delete_your_account_all_statistics_will_be_lost))

            // Кнопка "Да"
            builder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                dialog.dismiss() // Закрываем диалог
                viewModel.deleteStats()
                editor = sharedPref.edit()             //УДАЛЕНИЕ АККАУНТА
                editor.putString(NAME_KEY, null)
                editor.putString(SURNAME_KEY, null)
                editor.putString(ADDRESS_KEY, null)
                editor.putString(EXTRA_KEY, null)
                editor.apply()
                finish()
            }

            // Кнопка "Нет"
            builder.setNegativeButton(getString(R.string.no_back)) { dialog, _ ->
                dialog.dismiss()
            }

            // Показываем диалог
            builder.create().show()
        }

        binding.editProfilePr.setOnClickListener{
            startActivity(Intent(this, Registration::class.java))
            finish()
        }

        binding.statsButtonPr.setOnClickListener{
            startActivity(Intent(this, Stats::class.java))
        }
    }

    companion object{
        const val NAME_KEY = "name"
        const val SURNAME_KEY = "surname"
        const val ADDRESS_KEY = "address"
        const val EXTRA_KEY = "extra"
        const val PROFILE_KEY = "AccountPref"
    }
}