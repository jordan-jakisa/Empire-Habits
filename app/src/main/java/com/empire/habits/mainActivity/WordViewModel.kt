package com.empire.habits.ui.items

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import com.empire.habits.R
import com.empire.habits.entity.Word
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.util.*

class WordViewModel(private val repository: ItemRepository) : ViewModel() {
    lateinit var items: List<Word>
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()
    fun insert(word: Word) = viewModelScope.launch { repository.insert(word) }
    fun updateCount(count: Float, word: String) = viewModelScope.launch { repository.update(count, word) }
    fun currentHour(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    fun currentMinute(): Int = Calendar.getInstance().get(Calendar.MINUTE)
    fun countDownTimer(timeLeftTv: TextView, progressBar: ProgressBar, startBtn: MaterialButton, stopPhubing: TextView) {
        timeLeftTv.text = startBtn.context.getString(R.string._25)
        val timer = object : CountDownTimer(1560000, 60000){
            override fun onTick(millisUntilFinished: Long) {
                val minutesLeft = (millisUntilFinished / 1000)/60
                startBtn.text = "Pause"
                stopPhubing.visibility = View.VISIBLE
                if (minutesLeft.equals(0))  {
                    startBtn.setOnClickListener {
                        startBtn.text = "Start"
                    }
                }
                else if (!minutesLeft.equals(0)){
                    startBtn.setOnClickListener{
                        startBtn.text = "Resume"
                        onTick(millisUntilFinished)
                    }
                }
                else

                timeLeftTv.text = minutesLeft.toString()
                progressBar.progress = minutesLeft.toInt()*100/25
            }

            override fun onFinish() {
                timeLeftTv.text = 0.toString()
                startBtn.text = startBtn.context.getString(R.string.start)
                stopPhubing .visibility = View.GONE
                Toast.makeText(startBtn.context, "Congratulations", Toast.LENGTH_SHORT).show()
            }
        }
        timer.start()
    }

    class WordViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
}
