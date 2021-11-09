package com.empire.habits.ui.items

import androidx.annotation.WorkerThread
import com.empire.habits.databases.WordDao
import com.empire.habits.entity.Word
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(count: Float, word: String) {
        wordDao.updateCount(count, word)
    }
}