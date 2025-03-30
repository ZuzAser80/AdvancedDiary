package com.zuzaser.advanceddiary.model

import android.media.AudioTrack
import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zuzaser.advanceddiary.repository.DiaryRepository
import com.zuzaser.advanceddiary.room.AdvancedDiaryDatabase

@Entity(tableName = "entries")
data class DiaryEntryModel(
    @PrimaryKey val id : Int,
    val entryText : String,
    //storing only REFERENCES to files instead of BLOBs or some other bs
    val entryImage : String,
    val entryVoice : String,
    @Embedded var entryLocation: Location
) {
    fun getAllImages(database: AdvancedDiaryDatabase) : List<String> {
        return database.fromString(entryImage)
    }
}

class Location {
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}