package com.zuzaser.advanceddiary.repository

import androidx.lifecycle.LiveData
import com.zuzaser.advanceddiary.model.DiaryEntryModel
import com.zuzaser.advanceddiary.room.AdvancedDiaryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryRepository (private val dao : AdvancedDiaryDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addDiaryEntry(diaryEntry : DiaryEntryModel) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.add(diaryEntry)
        }
    }

    fun removeDiaryEntry(diaryEntry: DiaryEntryModel) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.removeEntry(diaryEntry)
        }
    }

    fun clearTable() {
        coroutineScope.launch(Dispatchers.IO) {
            dao.removeAll()
        }
    }

    val allEntries: LiveData<List<DiaryEntryModel>> = dao.getAllEntries()
}