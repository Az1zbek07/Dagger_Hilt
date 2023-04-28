package com.example.daggerthilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.daggerthilt.model.NoteResponseItem

@Database(entities = [NoteResponseItem::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}