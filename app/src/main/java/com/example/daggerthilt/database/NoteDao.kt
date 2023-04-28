package com.example.daggerthilt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.daggerthilt.model.NoteResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(noteResponseItem: NoteResponseItem)

    @Query("SELECT * FROM NoteResponseItem ORDER BY MyId")
    fun getLocalNotes(): Flow<List<NoteResponseItem>>
}