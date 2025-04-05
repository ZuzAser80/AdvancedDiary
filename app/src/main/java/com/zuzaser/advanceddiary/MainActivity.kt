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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import com.zuzaser.advanceddiary.repository.DiaryRepository
import com.zuzaser.advanceddiary.room.AdvancedDiaryDatabase
import com.zuzaser.advanceddiary.ui.theme.AdvancedDiaryTheme
import com.zuzaser.advanceddiary.view.DiaryEntryCreateView
import com.zuzaser.advanceddiary.view.DiaryEntryView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val paletteDb = AdvancedDiaryDatabase.getInstance(application)
            val paletteDao = paletteDb.diaryDao()
            val repository: DiaryRepository = DiaryRepository(paletteDao)

            val diaryEntries = repository.allEntries
            diaryEntries.observe(this, Observer {
                entries -> entries?.let {  }
            })
            AdvancedDiaryTheme {
                val scrollState = rememberScrollState()
                Column(modifier = Modifier.verticalScroll(scrollState).height(IntrinsicSize.Max), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                    val openCreation = remember { mutableStateOf(false) }
                    var view = DiaryEntryCreateView()
                    if (openCreation.value) {
                        view.GetView(repository, onFinish = {
                            openCreation.value = !openCreation.value
                        })
                    }
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        openCreation.value = !openCreation.value
                    }) { Icon(Icons.Filled.Add, contentDescription = "") }
                    if(diaryEntries.value != null) {
                        diaryEntries.let {
                            for (i in diaryEntries.value!!) {
                                if (i.getAllImages().isNotEmpty()) {
                                    DiaryEntryView().GetPreview(i)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}