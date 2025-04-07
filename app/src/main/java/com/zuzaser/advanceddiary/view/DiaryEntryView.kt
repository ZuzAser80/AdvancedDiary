package com.zuzaser.advanceddiary.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.zuzaser.advanceddiary.model.DiaryEntryModel
import java.io.File
import kotlin.random.Random

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
                for (i in diaryEntryModel.getAllImages()) {
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
        }
    }
    @Composable
    fun GetPreview(diaryEntryModel: DiaryEntryModel) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Gray),
        ) {
            Column(modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    diaryEntryModel.entryText.substring(
                        0,
                        if (diaryEntryModel.entryText.length > 20) 20 else diaryEntryModel.entryText.length
                    )
                )
                if (diaryEntryModel.getAllImages().isNotEmpty()) {
                    println("IMAGE: " + diaryEntryModel.getAllImages()[0])
                    val imgFile = File(diaryEntryModel.getAllImages()[0])

                    // on below line we are checking if the image file exist or not.
                    var imgBitmap: Bitmap? = null
                    if (imgFile.exists()) {
                        // on below line we are creating an image bitmap variable
                        // and adding a bitmap to it from image file.
                        imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    }

                    Image(
                        // on the below line we are specifying the drawable image for our image.
                        // painter = painterResource(id = courseList[it].languageImg),
                        painter = rememberImagePainter(data = imgBitmap),

                        // on the below line we are specifying
                        // content description for our image
                        contentDescription = "Image",

                        // on the below line we are setting the height
                        // and width for our image.
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}