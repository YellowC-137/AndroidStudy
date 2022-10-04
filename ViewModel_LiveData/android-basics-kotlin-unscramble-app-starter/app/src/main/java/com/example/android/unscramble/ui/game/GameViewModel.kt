package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = 0
    val score : Int = _score
    private var _currentWordCount = 0
    val currentWordCount:Int = _currentWordCount
    private lateinit var _currentScrambleWord: String
    val currentScrambledWord: String = _currentScrambleWord
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    init {
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        //viewmodel의 소멸
    }

    private fun getNextWord() {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambleWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord():Boolean{
        return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }
        else false
    }



    fun increaseScore(){
        _score+= SCORE_INCREASE
    }

    fun isUserWordCorrect(playersWord:String):Boolean{
        if (playersWord.equals(currentWord,true))
        {
            increaseScore()
            return true
        }
        else
            return false
    }

    fun reinitialize(){
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }


}