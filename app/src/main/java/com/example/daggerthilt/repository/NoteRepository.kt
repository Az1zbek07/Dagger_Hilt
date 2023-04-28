package com.example.daggerthilt.repository

import com.example.daggerthilt.database.NoteDao
import com.example.daggerthilt.model.NoteResponseItem
import com.example.daggerthilt.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepository(
    private val dao: NoteDao,
    private val apiService: ApiService
){
    suspend fun getRemoteNotes(): Flow<List<NoteResponseItem>> = flow {
        emit(apiService.getRemotePosts().body()?: emptyList())
    }
    suspend fun saveNote(noteResponseItem: NoteResponseItem) = dao.saveNote(noteResponseItem)
    fun getLocalNotes() = dao.getLocalNotes()
}