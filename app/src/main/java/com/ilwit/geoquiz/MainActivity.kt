package com.ilwit.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

   private val KEY_INDEX = "index"

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    lateinit var cheatButton:Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var goNextAct: Button
    private  val counter: TextView by lazy {
        findViewById(R.id.counter)
    }


    private val quizViewModel: QuizViewModel by lazy{
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex.value = currentIndex
        quizViewModel.currentIndex.observe(this) { index ->
            Toast.makeText(this, index.toString(), Toast.LENGTH_LONG).show()

            if (index == 0) {
                prevButton.visibility = View.GONE
            }
            if(index==quizViewModel.questionBank.size-1){
                nextButton.visibility = View.GONE
            }
        }

        cheatButton = findViewById(R.id.cheat_button)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_tview)
        prevButton = findViewById(R.id.prew_button)

        quizViewModel.currentIndex.observe(this){index->counter.setText(index.toString())
            }

        goNextAct = findViewById(R.id.goNextActivity)

        goNextAct.setOnClickListener {
            val i = Intent(this, SecondActivity::class.java)
            startActivity(i)
        }


        cheatButton.setOnClickListener{
            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
        }
        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }



        nextButton.setOnClickListener {
            prevButton.visibility = View.VISIBLE
            quizViewModel.moveToNext()
           updateQuestion()
            if (quizViewModel.currentIndex.value == quizViewModel.questionBank.size - 1) {
                nextButton.visibility = View.GONE
                updateQuestion()
            } else {
                updateQuestion()
            }

        }
        prevButton.setOnClickListener {
            nextButton.visibility = View.VISIBLE
            quizViewModel.moveToPrev()
            updateQuestion()
            if (quizViewModel.currentIndex.value == 0) {
                prevButton.visibility = View.GONE
                updateQuestion()
            } else {
                updateQuestion()
            }
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i("TAG", "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex.value!!)
    }


    private fun updateQuestion() {

        val questionTextId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()
    }

}