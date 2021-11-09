package com.empire.habits.mainActivity

import android.app.Application
import com.empire.habits.databases.WordRoomDatabase
import com.empire.habits.ui.items.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ItemRepository(database.wordDao()) }
}