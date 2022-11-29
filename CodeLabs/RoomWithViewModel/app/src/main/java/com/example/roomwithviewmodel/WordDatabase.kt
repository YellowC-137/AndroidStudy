package com.example.roomwithviewmodel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase()
{
    abstract fun wordDao(): WordDao

    companion object{

        //내부에 저장하기
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                        ).build()
            INSTANCE = instance

                instance
            }
        }

    }



}