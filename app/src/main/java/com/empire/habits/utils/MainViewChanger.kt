package com.empire.habits.utils

import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.empire.habits.adapters.ItemListAdapter
import com.empire.habits.entity.Word
import com.google.android.material.button.MaterialButton

interface MainViewChanger {

    var toggleStatus: Int

    fun insertWord(word: Word)

    fun observeWords(adapter: ItemListAdapter)

    fun getItems(): List<Word>?

    fun updateCount(count: Float, word: String)

    fun itemSize(view: ViewGroup)

    fun startAlarm(title: String, content: String, hour: Int, minute: Int)

    fun showSnackBar()

    fun startTimer(timeLeftTv: TextView, progressBar: ProgressBar, startBtn: MaterialButton, stopPhubing: TextView)
}