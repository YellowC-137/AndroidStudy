package com.example.room_viewmodel

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
    //앱에서 처음 필요할때만 만들어야함
}