package com.example.thenoteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "notes")
@Parcelize
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val noteTital: String,
    val noteDesc: String,
    val colorCode: String

): Parcelable
