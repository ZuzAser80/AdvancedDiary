package com.zuzaser.advanceddiary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zuzaser.advanceddiary.model.DiaryEntryModel

class DiaryEntryView  {
    @Composable
    fun GetView(diaryEntryModel: DiaryEntryModel) {
        Scaffold(modifier = Modifier.fillMaxSize()) { internalPadding ->
            Box(modifier = Modifier
                .padding(internalPadding)
                .background(Color.Green)
                .fillMaxWidth()
            )
        }
    }
}