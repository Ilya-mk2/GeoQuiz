package com.ilwit.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var goNextAct: Button

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_tview)
        prevButton = findViewById(R.id.prew_button)

        goNextAct = findViewById(R.id.goNextActivity)

        goNextAct.setOnClickListener {
            val i = Intent(this, SecondActivity::class.java)
            startActivity(i)
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        if (currentIndex == 0) {
            prevButton.visibility = View.GONE
        }

        nextButton.setOnClickListener {
            prevButton.visibility = View.VISIBLE
            currentIndex++
            if (currentIndex >= questionBank.size - 1) {
                nextButton.visibility = View.GONE
                updateQuestion()
            } else {
                updateQuestion()
            }
        }
        prevButton.setOnClickListener {
            nextButton.visibility = View.VISIBLE
            currentIndex--
            if (currentIndex == 0) {
                prevButton.visibility = View.GONE
                updateQuestion()
            } else {
                updateQuestion()
            }
        }
        updateQuestion()

        Log.d("TAG", "created")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "started")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "resumed")

    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "destroyed")
    }

    private fun updateQuestion() {
        val questionTextId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()
    }

}