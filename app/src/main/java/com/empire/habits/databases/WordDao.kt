package com.empire.habits.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.empire.habits.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM `table`")
    fun getItems(): Flow<List<Word>>

    @Query("SELECT * FROM `table`")
    fun getAllItems(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM `table`")
    suspend fun deleteAll()

    @Query("UPDATE `table` SET count=:count WHERE word=:word")
    suspend fun updateCount(count: Float, word: String)
}