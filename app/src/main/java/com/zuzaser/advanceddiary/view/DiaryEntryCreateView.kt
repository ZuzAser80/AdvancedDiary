package com.zuzaser.advanceddiary.view

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

class DiaryEntryCreateView {
    @Composable
    fun GetView() {
        var imageData = remember { mutableStateOf<List<Uri>>(emptyList()) }
        var voiceData = remember { mutableStateOf<List<Uri>>(emptyList()) }
        val multiplePhotoPicker  =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2)) {
                imageData.value = it
            }
        val multipleAudioPicker  =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) {
                voiceData.value = it
            }
        Scaffold(modifier = Modifier.fillMaxSize()) { internalPadding ->
            Box(modifier = Modifier
                .padding(internalPadding)
                .background(Color.Red)
                .fillMaxSize()
            ) {
                TextField(value = "Entry text here...", onValueChange = { entryText -> {} })
                //photo picker button
                Button(onClick = {
                    //here we are going to add logic for picking image
                    multiplePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }, Modifier.background(Color.Blue), content = {
                    Text(text = "Select Image From Gallery")
                })
                Button(onClick = {
                    //here we are going to add logic for picking image
                    multipleAudioPicker.launch(
                        "audio/*"
                    )
                }, Modifier.background(Color.Black), content = {
                    Text(text = "SELECT AUDIO")
                })
                //
            }
        }
    }

    @Composable
    fun RequestPermission(onGranted: () -> Unit) {
        val context = LocalContext.current
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_AUDIO
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onGranted()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(permission)
        }
    }

//    fun fetchSongsFromDevice(context: Context): List<Song> {
//        val songList = mutableListOf<Song>()
//        val contentResolver = context.contentResolver
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//
//        val projection = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.DATA // File path for the song
//        )
//
//        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
//        val cursor = contentResolver.query(uri, projection, selection, null, null)
//
//        cursor?.use {
//            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
//            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
//            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
//            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
//
//            while (it.moveToNext()) {
//                val id = it.getLong(idColumn)
//                val title = it.getString(titleColumn)
//                val artist = it.getString(artistColumn)
//                val data = it.getString(dataColumn)
//
//                songList.add(Song(id, title, artist, data))
//            }
//        }
//
//        return songList
//    }
}