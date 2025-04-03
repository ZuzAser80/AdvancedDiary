package com.zuzaser.advanceddiary.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import java.io.File

class DiaryEntryCreateView {
    @Preview(showBackground = true)
    @Composable
    fun GetView() {
        var imageData = remember { mutableStateOf<List<Uri>>(emptyList()) }
        val multiplePhotoPicker  =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2)) {
                imageData.value += it
            }
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
                Button(onClick = {

                }, content = { Text(text = "Finish")
                })
            })
        }
    }

    //stolen
    @Composable
    fun ExpandableText(modifier: Modifier = Modifier, text: String = "", content: @Composable () -> Unit) {
        Column(Modifier.fillMaxSize(), horizontalAlignment =
        Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            var textData = remember { mutableStateOf("") }
            var showMore = remember { mutableStateOf(false) }
            Column(modifier = Modifier.padding(20.dp)) {

                // Creating a clickable modifier that consists text
                Column(modifier = Modifier
                    .animateContentSize(animationSpec = tween(100))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showMore.value = !showMore.value }) {

                    // if showMore is true, the Text will expand
                    // Else Text will be restricted to 3 Lines of display
                    if (showMore.value) {
                        TextField(
                            textData.value,
                            {textData.value = it},
                            textStyle = TextStyle(fontSize =  28.sp),
                            placeholder = { Text("Entry text here...") }
                        )
                    } else {
                        TextField(
                            textData.value,
                            {textData.value = it},
                            textStyle = TextStyle(fontSize =  28.sp),
                            placeholder = { Text("Entry text here...") },
                            maxLines = 10
                        )
                    }
                }
            }
            content()
        }
    }
}