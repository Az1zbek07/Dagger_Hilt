package com.example.daggerthilt.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NoteResponseItem(
    @PrimaryKey(autoGenerate = true)
    val myId: Int = 0,
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
): Parcelable