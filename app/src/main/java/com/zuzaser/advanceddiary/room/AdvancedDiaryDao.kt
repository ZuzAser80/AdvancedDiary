package com.zuzaser.advanceddiary.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zuzaser.advanceddiary.model.DiaryEntryModel

@Dao
interface AdvancedDiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(paletteModel: DiaryEntryModel)

    @Query("SELECT * FROM entries")
    fun getAllEntries(): LiveData<List<DiaryEntryModel>>

    @Query("DELETE FROM entries")
    fun removeAll()

    @Delete
    fun removeEntry(entryModel: DiaryEntryModel)
}