package com.zuzaser.advanceddiary.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zuzaser.advanceddiary.model.DiaryEntryModel
import com.zuzaser.advanceddiary.model.Location
import com.zuzaser.advanceddiary.util.UriPathFinder
import com.zuzaser.advanceddiary.viewmodel.DiaryViewModel
import kotlin.random.Random

class DiaryEntryCreateView {
    @Composable
    fun GetView(diaryViewModel: DiaryViewModel, onFinish: () -> Unit) {
        var imageData = remember { mutableStateOf<List<Uri>>(emptyList()) }
        val multiplePhotoPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2)) { imageData.value += it }
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ExpandableText(content = {
                Button(onClick = {
                    multiplePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    ) }, content = { Text(text = "Select Image From Gallery")
                })
                imageData.let {
                    for (i in imageData.value) {
                        Box(modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .padding(10.dp),
                        ) {
                            AsyncImage(
                                model = i,
                                contentDescription = "Image",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
                var i = emptyList<String>()
                for (j in imageData.value) {
                    i += UriPathFinder().getPath(LocalContext.current, j)!!
                }
                Button(onClick = {
                    diaryViewModel.addEntry(DiaryEntryModel(Random.nextInt(1024 * 10), it.value, i.toString(), Location(0.0, 0.0)))
                    onFinish()
                }, content = { Text(text = "Finish")
                })
            })
        }
    }

    //stolen
    @Composable
    fun ExpandableText(modifier: Modifier = Modifier, content: @Composable (textData: MutableState<String>) -> Unit) {
        Column(Modifier.fillMaxSize(), horizontalAlignment =
        Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            var textData = remember { mutableStateOf("") }
            var showMore = remember { mutableStateOf(false) }
            Column(modifier = Modifier.padding(20.dp)) {
                Column(modifier = Modifier
                    .animateContentSize(animationSpec = tween(100))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showMore.value = !showMore.value }) {
                    if (showMore.value) {
                        TextField(
                            textData.value,
                            {textData.value = it},
                            textStyle = TextStyle(fontSize =  28.sp),
                            placeholder = { Text("Entry text here...") },
                            modifier = modifier
                        )
                    } else {
                        TextField(
                            textData.value,
                            {textData.value = it},
                            textStyle = TextStyle(fontSize =  28.sp),
                            placeholder = { Text("Entry text here...") },
                            maxLines = 20
                        )
                    }
                }
            }
            content(textData)
        }
    }
}