package com.example.roomwithviewmodel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordDatabase : RoomDatabase()
{
    abstract fun wordDao(): WordDao

    companion object{

        //내부에 저장하기
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context
        ,scope: CoroutineScope): WordDatabase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                        ).addCallback(WordDatabaseCallback(scope)).build()
            INSTANCE = instance

                instance
            }
        }

    }

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
            // Delete all content here.
            wordDao.deleteAll()

            // Add sample words.
            var word = Word(99,"test")
            wordDao.insert(word)

            // TODO: Add your own words!
        }
    }

}