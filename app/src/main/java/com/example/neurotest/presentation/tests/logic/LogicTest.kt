package com.example.neurotest.presentation.tests.logic

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.neurotest.databinding.ActivityLogicTestBinding
import com.example.neurotest.R
import com.example.neurotest.presentation.StatsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class LogicTest : AppCompatActivity() {
    private lateinit var binding: ActivityLogicTestBinding

    private val viewModel: StatsVM by viewModels()

    private var total = 4

    private var answeredTimes = 0
    private var correctAnswers = 0
    private val handler = Handler(Looper.getMainLooper())

    private val wordChains = mutableListOf(
        WordChain(listOf("Apple", "Car", "Orange", "Banana"), 2), // "Car" лишнее
        WordChain(listOf("Fish", "Dog", "Rabbit", "Cat"), 1), // "Fish" лишнее
        WordChain(listOf("Red", "Blue", "Green", "Circle"), 4), // "Circle" лишнее
        WordChain(listOf("Man", "Woman", "Tree", "Child"), 3), // "Tree" лишнее
        WordChain(listOf("Book", "Pen", "Pencil", "House"), 4), // "House" лишнее
        WordChain(listOf("Car", "Summer", "Spring", "Winter"), 1), // "Car" лишнее
        WordChain(listOf("Sun", "Pepper", "Sugar", "Salt"), 1), // "Sun" лишнее
        WordChain(listOf("Paris", "Ocean", "Rome", "Berlin"), 2), // "Ocean" лишнее
        WordChain(listOf("Math", "History", "English", "Cloud"), 4), // "Cloud" лишнее
        WordChain(listOf("Football", "Fish", "Tennis", "Basketball"), 2) // "Fish" лишнее
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogicTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        total = intent.getIntExtra(LogicTestSettings.LOGIC_TEST_QUESTIONS_KEY, 4)

        setWords()
    }

    private fun nextChain(): WordChain{
        val random = (0..wordChains.size-1).random()
        val toReturn = wordChains[random]
        wordChains.removeAt(random)
        return toReturn
    }

    private fun setWords(){
        val chain = nextChain()
        val shuffledWords = chain.words.shuffled()

        // Присваиваем перемешанные слова кнопкам
        binding.btn1Lt.text = shuffledWords[0]
        binding.btn2Lt.text = shuffledWords[1]
        binding.btn3Lt.text = shuffledWords[2]
        binding.btn4Lt.text = shuffledWords[3]

        binding.btn1Lt.setOnClickListener{ onButtonClick(binding.btn1Lt, chain,1) }
        binding.btn2Lt.setOnClickListener{ onButtonClick(binding.btn2Lt, chain,2) }
        binding.btn3Lt.setOnClickListener{ onButtonClick(binding.btn3Lt, chain,3) }
        binding.btn4Lt.setOnClickListener{ onButtonClick(binding.btn4Lt, chain,4) }
    }

    private fun onButtonClick(button: Button, chain: WordChain, index: Int){
        lifecycleScope.launch{
            if(chain.words[chain.correctIndex-1] == button.text){
                correctAnswers += 1
                handler.post{
                    button.backgroundTintList = ContextCompat.getColorStateList(this@LogicTest, R.color.green)
                }
            }else{
                handler.post{
                    button.backgroundTintList = ContextCompat.getColorStateList(this@LogicTest, R.color.red)
                }
            }
            handler.post{ activatingButtons(false) }
            delay(1000)
            handler.post{ activatingButtons(true) }
            handler.post{
                resetColors()
                answeredTimes += 1
                if(total == answeredTimes){
                    viewModel.addOrUpdateLogicResult("$correctAnswers/$total")
                    congratsWindow()
                }else{
                    setWords()
                }
            }
        }
    }

    private fun congratsWindow(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.congratulation))
        builder.setMessage(getString(R.string.you_have_answered_correctly_times, correctAnswers.toString(), total.toString()))
        // Кнопка ОК для закрытия диалога
        builder.setPositiveButton("ОК") { dialog, _ ->
            dialog.dismiss() // Закрываем диалог при нажатии на кнопку
            finish()
        }
        // Показываем диалог
        builder.show()
    }

    private fun resetColors(){
        binding.btn1Lt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_blue)
        binding.btn2Lt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_blue)
        binding.btn3Lt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_blue)
        binding.btn4Lt.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_blue)
    }

    private fun activatingButtons(bool: Boolean){
        binding.btn1Lt.isEnabled = bool
        binding.btn2Lt.isEnabled = bool
        binding.btn3Lt.isEnabled = bool
        binding.btn4Lt.isEnabled = bool
    }
}