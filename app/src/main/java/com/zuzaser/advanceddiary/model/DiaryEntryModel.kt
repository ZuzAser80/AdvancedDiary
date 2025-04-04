package com.zuzaser.advanceddiary.model

import android.media.AudioTrack
import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zuzaser.advanceddiary.repository.DiaryRepository
import com.zuzaser.advanceddiary.room.AdvancedDiaryDatabase

@Entity(tableName = "entries")
data class DiaryEntryModel(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val entryText : String,
    //storing only REFERENCES to files instead of BLOBs or some other bs
    val entryImage : String,
    @Embedded var entryLocation: Location
) {
    fun getAllImages() : List<String> {
        return fromString(entryImage)
    }

    @TypeConverter
    fun toString(entity: List<String>): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun fromString(serialized: String): List<String> {
        var r : List<String> = serialized.substring(1, serialized.length - 1).split(", ")
        return r
    }
}

class Location (
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)