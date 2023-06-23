package com.ilwit.geoquiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private  val TAG = "QuizViewModel"
    var currentIndex = MutableLiveData<Int>(0)
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer : Boolean
    get() = questionBank[currentIndex.value!!].answer

    val currentQuestionText: Int
    get() = questionBank[currentIndex.value!!].textResId

    fun moveToNext(){
        currentIndex.value= currentIndex.value?.inc()
        Log.d("TAG", "move to next, currentIndex value is ${currentIndex.value}")
        //currentIndex.postValue(currentIndex.value?.inc())
    }
    fun moveToPrev(){
        currentIndex.value= currentIndex.value?.dec()
       // currentIndex.postValue(currentIndex.value?.dec())
    }

}