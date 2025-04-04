package com.zuzaser.advanceddiary

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.zuzaser.advanceddiary.model.DiaryEntryModel
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
                Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.background(Color.Magenta).fillMaxWidth())
                    val openCreation = remember { mutableStateOf(false) }

                    var view = DiaryEntryCreateView()
                    if (openCreation.value) {
                        view.GetView(repository, onFinish = {
                            openCreation.value = !openCreation.value
                        })
                    }
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        openCreation.value = !openCreation.value
                    }) { Icon(Icons.Filled.Add, contentDescription = "соси") }

                        diaryEntries.let {
                            for (i in diaryEntries.value!!) {
                                Box(modifier = Modifier.background(Color.Magenta).width(100.dp).height(100.dp)) {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdvancedDiaryTheme {
        Greeting("Android")
    }
}