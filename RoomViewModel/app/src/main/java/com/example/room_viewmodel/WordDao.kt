package com.example.room_viewmodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY words ASC")
    fun getWords(): Flow<List<Word>>
    //db 의 변경사항 관찰하기, 이후 Flow를 LiveData로 변경

    //Replace vs Ignore
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    //suspend fun -> 정지함수, 코루틴 내부에서 실행해야함.
}