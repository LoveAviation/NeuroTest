package com.example.neurotest.presentation.tests.speech

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.neurotest.R
import com.example.neurotest.databinding.ActivitySpeechTestBinding
import com.example.neurotest.presentation.StatsVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.random.Random

@AndroidEntryPoint
class SpeechTest : AppCompatActivity() {
    private lateinit var binding: ActivitySpeechTestBinding
    private lateinit var speechRecognizer: SpeechRecognizer

    private val viewModel: StatsVM by viewModels()

    private val listOfWords = arrayOf<String>(
        "paper", "toy", "doctor", "phone", "cake",
        "police", "car", "fire", "water", "bottle",
        "closet", "chair", "table", "window", "door",
        "computer", "house", "money", "dog", "bread")
    private var numberOfWords = 4
    private var correctAnswers = 0
    private var testWords = mutableListOf<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeechTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberOfWords = intent.getIntExtra(SpeechTestSettings.SPEECH_TEST_KEY, 4)
        testWords = selectWords().toMutableList()


        Glide.with(binding.root)
            .load(findImage(testWords[0]))
            .into(binding.imageSpt)
        binding.counterSpt.text = "${numberOfWords - testWords.size + 1}/${numberOfWords}"
        binding.microphoneButtonSpt.setOnClickListener{
            hearWord()
        }
    }

    private fun findImage(word: String): Int{
        return when(word){
            "paper" -> R.drawable.paper
            "toy" -> R.drawable.toy
            "doctor" -> R.drawable.doctor
            "phone" -> R.drawable.phone
            "cake" -> R.drawable.cake
            "police" -> R.drawable.police
            "car" -> R.drawable.car
            "fire" -> R.drawable.fire
            "water" -> R.drawable.water
            "bottle" -> R.drawable.bottle
            "closet" -> R.drawable.closet
            "chair" -> R.drawable.chair
            "table" -> R.drawable.table
            "window" -> R.drawable.window
            "door" -> R.drawable.door
            "computer" -> R.drawable.computer
            "house" -> R.drawable.house
            "money" -> R.drawable.money
            "dog" -> R.drawable.dog
            "bread" -> R.drawable.bread
            else -> { 0 }
        }
    }

    private fun selectWords(): List<String> {
        // Создаём список уникальных случайных чисел от 0 до 19
        val uniqueIndices = mutableSetOf<Int>()
        while (uniqueIndices.size < numberOfWords) {
            val randomIndex = Random.nextInt(0, listOfWords.size)
            uniqueIndices.add(randomIndex)
        }

        // Выбираем слова по уникальным индексам
        return uniqueIndices.map { listOfWords[it] }
    }

    private fun setupSpeechRecognizer() {
        binding.microphoneButtonSpt.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext, getString(R.string.say), Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                Toast.makeText(applicationContext, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                Log.d(TAG, error.toString())
                binding.microphoneButtonSpt.clearColorFilter()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val recognizedText = matches?.get(0) ?: ""
                binding.microphoneButtonSpt.clearColorFilter()

                binding.saidSpt.visibility = View.VISIBLE
                binding.nextWordSpt.visibility = View.VISIBLE
                binding.saidSpt.text = getString(R.string.you_said, recognizedText)

                binding.nextWordSpt.setOnClickListener{
                    if(recognizedText == testWords[0]) { correctAnswers += 1 }
                    else { Snackbar.make(binding.root, getString(R.string.mistake_previous_word_was, testWords[0]), Snackbar.LENGTH_LONG).show() }
                    nextWord()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    @SuppressLint("SetTextI18n")
    private fun nextWord(){
        testWords.removeAt(0)
        if(testWords.isNotEmpty()) {
            Glide.with(binding.root)
                .load(findImage(testWords[0]))
                .into(binding.imageSpt)
            binding.counterSpt.text = "${numberOfWords - testWords.size + 1}/${numberOfWords}"
            binding.saidSpt.visibility = View.GONE
            binding.nextWordSpt.visibility = View.GONE
        }else{
            viewModel.addOrUpdateSpeechResult("$correctAnswers/$numberOfWords")
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.congratulation))
            builder.setMessage(
                getString(R.string.you_have_correctly_recognized_pictures, correctAnswers.toString()))
            // Кнопка ОК для закрытия диалога
            builder.setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss() // Закрываем диалог при нажатии на кнопку
                finish()
            }
            // Показываем диалог
            builder.show()
        }
    }

    private fun hearWord() {
        setupSpeechRecognizer()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-EN")
        }
        speechRecognizer.startListening(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::speechRecognizer.isInitialized) {speechRecognizer.destroy()}
    }
}