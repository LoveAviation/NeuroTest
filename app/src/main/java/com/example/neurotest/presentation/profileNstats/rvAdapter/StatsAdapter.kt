package com.example.neurotest.presentation.profileNstats.rvAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neurotest.data.DayResult
import com.example.neurotest.databinding.StatFieldBinding

class StatsAdapter(private val items: List<DayResult>) :
    RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    class StatsViewHolder(val binding: StatFieldBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val binding = StatFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            statDate.text = "Date: ${item.date}"
            attentionStatText.text = item.attentionTest
            memoryStatText.text = item.memoryTest
            logicStatText.text = item.logicTest
            reactionStatText.text = item.reactionTest
            mathStatText.text = item.mathTest
            speechStatText.text = item.speechTest
        }
    }

    override fun getItemCount(): Int = items.size
}