package com.example.neurotest.presentation.tests.math

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.neurotest.databinding.ActivityMathTestBinding
import com.example.neurotest.R
import com.example.neurotest.presentation.StatsVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MathTest : AppCompatActivity() {
    private lateinit var binding: ActivityMathTestBinding

    private val viewModel : StatsVM by viewModels()

    private val handler = Handler(Looper.getMainLooper())

    private var numberOfTests = 5
    private var counter = 0

    private var answer = 0

    private var rightAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberOfTests = intent.getIntExtra(MathTestSettings.MATH_TEST_EXAMPLES_KEY, 5)

        createExample()

        binding.answerFieldMt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkAnswer()
                true
            } else {
                false
            }
        }

        binding.checkButton.setOnClickListener{
            checkAnswer()
        }
    }

    private fun checkAnswer(){
        if(binding.answerFieldMt.text?.trim().toString() == answer.toString()){
            rightAnswers += 1
            binding.exampleTextMt.setTextColor(getColor(R.color.green))
            binding.answerFieldMt.setTextColor(getColor(R.color.green))
            lifecycleScope.launch{ delay(600)
                handler.post{ if (notLastTest()) { createExample() } } }
        }else if(binding.answerFieldMt.text?.trim().toString() != answer.toString()){
            binding.exampleTextMt.setTextColor(getColor(R.color.red))
            binding.answerFieldMt.setTextColor(getColor(R.color.red))
            lifecycleScope.launch{ delay(600)
                handler.post{ if (notLastTest()) { createExample() } } }
        }else{
            Snackbar.make(binding.root, getString(R.string.firstly_enter_the_answer_in_the_line), Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun createExample(){
        binding.answerFieldMt.setText("")
        counter += 1
        binding.h1textMt.text = "$counter/$numberOfTests"
        binding.exampleTextMt.setTextColor(getColor(R.color.black))
        binding.answerFieldMt.setTextColor(getColor(R.color.black))
        var x = 0
        var y = 0
        var smoooooooooooooooooooooooooooooooooooooothOperator = listOf('+', '-', '*', '/').random()

        when(smoooooooooooooooooooooooooooooooooooooothOperator){
            '+' -> {
                x = (1..100).random(); y = (1..100).random()
                answer = x + y
                binding.exampleTextMt.text = "$x + $y ="
            }
            '-' -> {
                x = (1..100).random(); y = (1 until x).random()
                answer = x - y
                binding.exampleTextMt.text = "$x - $y ="
            }
            '*' -> {
                x = (1..10).random(); y = (1..10).random()
                answer =  x * y
                binding.exampleTextMt.text = "$x * $y ="
            }
            '/' -> {
                x = (1..10).random(); y = (1..10).random()
                answer = (x * y) / y
                binding.exampleTextMt.text = "${x * y} / $y ="
            }
        }
    }

    private fun notLastTest() : Boolean{
        if(numberOfTests == counter){
            viewModel.addOrUpdateMathResult("$rightAnswers/$numberOfTests")
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.congratulation))
            builder.setMessage("You solved $rightAnswers/$numberOfTests examples correctly")
            builder.setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            builder.show()
            return false
        }else{
            return true
        }
    }
}