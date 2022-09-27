package com.example.room_viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository):ViewModel() {
    val wordsList: LiveData<List<Word>> = repository.wordList.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

}


