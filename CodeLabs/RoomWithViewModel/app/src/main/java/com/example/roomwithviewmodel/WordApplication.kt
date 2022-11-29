package com.example.roomwithviewmodel

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {
    val database by lazy { WordDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}