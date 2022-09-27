package com.example.room_viewmodel

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val wordList: Flow<List<Word>> = wordDao.getWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
    //repository는 데이터를 가져올때 사용?
    //delete
}