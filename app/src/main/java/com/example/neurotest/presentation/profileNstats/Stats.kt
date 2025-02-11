package com.example.neurotest.presentation.profileNstats

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neurotest.databinding.ActivityStatsBinding
import com.example.neurotest.presentation.StatsVM
import com.example.neurotest.presentation.profileNstats.rvAdapter.StatsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Stats : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding

    private val viewModel : StatsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.statsBackButton.setOnClickListener{ finish() }

        binding.dataRvStat.layoutManager = LinearLayoutManager(this)

        startLoading()
        viewModel.getLastSevenDates()
        viewModel.favForms.observe(this){ stat ->
            if(stat != null){
                stopLoading()
                if(stat.isEmpty()){
                    binding.emptyDataStat.visibility = View.VISIBLE
                }else{
                    binding.dataRvStat.adapter = StatsAdapter(stat)
                }
            }
        }
    }

    private fun startLoading(){
        binding.emptyDataStat.visibility = View.GONE
        binding.dataRvStat.visibility = View.GONE
        binding.loadingBarStat.visibility = View.VISIBLE
    }

    private fun stopLoading(){
        binding.emptyDataStat.visibility = View.GONE
        binding.dataRvStat.visibility = View.VISIBLE
        binding.loadingBarStat.visibility = View.GONE
    }
}