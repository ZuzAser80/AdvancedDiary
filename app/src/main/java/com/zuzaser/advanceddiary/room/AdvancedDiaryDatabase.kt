package com.zuzaser.advanceddiary.room

import android.content.Context
import android.util.JsonWriter
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zuzaser.advanceddiary.model.DiaryEntryModel

@Database(entities = [DiaryEntryModel::class], version = 3, exportSchema = false)
abstract class AdvancedDiaryDatabase : RoomDatabase() {

    abstract fun diaryDao() : AdvancedDiaryDao

    companion object {
        private var INSTANCE: AdvancedDiaryDatabase? = null
        fun getInstance(context: Context): AdvancedDiaryDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AdvancedDiaryDatabase::class.java,
                        "Entries_database"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}