package com.example.room_viewmodel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//exportSchema 는 빌드오류 제거용
@Database(entities = [Word::class], version = 2, exportSchema = false)
public abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            dbInstance?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()
                    //wordDao.deleteAll()
                    //wordDao.getWords()
                    //onCreate시 scope 실행해서 단어 추가
                }
            }
        }

    }

    companion object {
        @Volatile
        private var dbInstance: WordDatabase? = null

        //scope 범위도 전달해준다.
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordDatabase {
            return dbInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                dbInstance = instance

                instance
            }
        }
    }


}