package com.zuzaser.advanceddiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zuzaser.advanceddiary.repository.DiaryRepository
import com.zuzaser.advanceddiary.room.AdvancedDiaryDatabase
import com.zuzaser.advanceddiary.view.DiaryEntryCreateView
import com.zuzaser.advanceddiary.view.DiaryEntryView
import com.zuzaser.advanceddiary.viewmodel.DiaryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val paletteDb = AdvancedDiaryDatabase.getInstance(application)
        val paletteDao = paletteDb.diaryDao()
        val diaryViewModel = DiaryViewModel(DiaryRepository(paletteDao))

        setContent {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .height(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val openCreation = remember { mutableStateOf(false) }
                if (openCreation.value) {
                    DiaryEntryCreateView().GetView(diaryViewModel, onFinish = {
                        openCreation.value = !openCreation.value
                    })
                }

                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    openCreation.value = !openCreation.value
                })
                { Icon(Icons.Filled.Add, contentDescription = "") }

                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    diaryViewModel.clearAll()
                })
                { Icon(Icons.Filled.Delete, contentDescription = "") }
                
                val dE = diaryViewModel.allEntries.observeAsState()
                if (dE.value != null) {
                    println(dE.value!!.size)
                    for (i in dE.value) {
                        DiaryEntryView().GetPreview(i)
                    }
                }
            }

        }
    }
}