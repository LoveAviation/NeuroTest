package com.example.neurotest.presentation.doctor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.neurotest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingScreen : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        lifecycleScope.launch{
            val time = (2..6).random().toLong()
            delay(time*1000)
            handler.post{
                startActivity(Intent(this@LoadingScreen, DoctorChat::class.java))
                finish()
            }
        }

    }
}