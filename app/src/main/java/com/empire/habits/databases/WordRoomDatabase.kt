package com.empire.habits.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.empire.habits.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 3)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                        WordRoomDatabase::class.java,
                        "table").addCallback(WordDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }

    }
}