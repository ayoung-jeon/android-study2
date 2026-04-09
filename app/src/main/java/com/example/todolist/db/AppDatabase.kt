package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1) //조건1
abstract class AppDatabase : RoomDatabase() { //조건2

    abstract fun getTodoDao(): TodoDao //조건3

    companion object {
        private const val DATABASE_NAME = "db_todo" //데이터베이스 이름, 임의로 지정

        @Volatile
        private var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return appDatabase ?: synchronized(this) {
                appDatabase ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { appDatabase = it }
            }
        }
    }
}