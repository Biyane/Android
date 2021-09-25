package com.example.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity123"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var restart: Button
    private lateinit var cheatButton: Button
    private lateinit var cheatTextView: TextView
    private var cntOfRightAnswers = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        restart = findViewById(R.id.restart)
        cheatButton = findViewById(R.id.cheat_button)
        cheatTextView = findViewById(R.id.cheat_button_counter)
        nextButton.isEnabled = false

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    quizViewModel.cheatCounter--
                    cheatTextView.text = getString(R.string.cheat_number, quizViewModel.cheatCounter)
                    cheatButton.isEnabled = quizViewModel.cheatCounter != 0
                    Log.d("hello2", quizViewModel.cheatCounter.toString())
                    quizViewModel.isCheater =
                        result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
                }

            }
        cheatTextView.text = getString(R.string.cheat_number, quizViewModel.cheatCounter)
        Log.d("hello", quizViewModel.cheatCounter.toString())
        trueButton.setOnClickListener {
            nextButton.isEnabled = true
            checkAnswer(true)
            disableButtons()
            if (quizViewModel.currentIndex == quizViewModel.sizeOfQuestions - 1) {
                nextButton.isEnabled = false
                restart.visibility = View.VISIBLE
                Handler(mainLooper).postDelayed({
                    announceResult()
                }, 1000)
            }
        }
        falseButton.setOnClickListener {
            nextButton.isEnabled = true
            disableButtons()
            checkAnswer(false)
            if (quizViewModel.currentIndex == quizViewModel.sizeOfQuestions - 1) {
                nextButton.isEnabled = false
                restart.visibility = View.VISIBLE
                Handler(mainLooper).postDelayed({
                    announceResult()
                }, 1000)
            }
        }

        nextButton.setOnClickListener {
            nextButton.isEnabled = false
            quizViewModel.isCheater = false
            quizViewModel.moveToNext()
            updateQuestion()
            enableButton()
            if (quizViewModel.currentIndex == quizViewModel.sizeOfQuestions - 1) {
                nextButton.isEnabled = false
            }
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this, answerIsTrue)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val options = ActivityOptionsCompat.makeClipRevealAnimation(it, 0, 0, it.width, it.height)
                startForResult.launch(intent, options)
            } else {
                startForResult.launch(intent)
            }
        }

        restart.setOnClickListener {
            quizViewModel.currentIndex = 0
            cheatButton.isEnabled = true
            quizViewModel.cheatCounter = 3
            updateQuestion()
            enableButton()
            restart.visibility = View.INVISIBLE
            cntOfRightAnswers = 0
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> {
                cntOfRightAnswers++
                R.string.correct_toast
            }
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableButtons() {
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun enableButton() {
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun announceResult() {
        Toast.makeText(
            this,
            getString(R.string.correct_answers, 100 / 6 * cntOfRightAnswers),
            Toast.LENGTH_LONG
        ).apply {
            setGravity(Gravity.TOP, 0, 0)
            show()
        }
    }
}