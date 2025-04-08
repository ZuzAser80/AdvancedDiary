package com.zuzaser.advanceddiary.viewmodel

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import com.zuzaser.advanceddiary.model.DiaryEntryModel
import com.zuzaser.advanceddiary.repository.DiaryRepository

class DiaryViewModel {

    val diaryRepository : DiaryRepository
    val allEntries : LiveData<List<DiaryEntryModel>>

    constructor(_diaryRepository : DiaryRepository){
        diaryRepository = _diaryRepository
        allEntries = diaryRepository.allEntries
    }

    fun addEntry(diaryEntryModel: DiaryEntryModel) {
        diaryRepository.addDiaryEntry(diaryEntryModel)
    }

    fun clearAll() { diaryRepository.clearTable() }
}