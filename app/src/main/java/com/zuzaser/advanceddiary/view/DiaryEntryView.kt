package com.zuzaser.advanceddiary.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.platform.LocalContext
import com.zuzaser.advanceddiary.model.DiaryEntryModel

class DiaryEntryView  {
    @Composable
    fun GetView(diaryEntryModel: DiaryEntryModel) {
        Scaffold(modifier = Modifier.fillMaxSize()) { internalPadding ->
            Box(modifier = Modifier
                .padding(internalPadding)
                .background(Color.Green)
                .fillMaxWidth()
            ) {
                Text(diaryEntryModel.entryText)
            }
        }
    }
}