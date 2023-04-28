package com.example.daggerthilt.viewmodel

import com.example.daggerthilt.model.NoteResponseItem

sealed class State {
    object Empty: State()
    object Loading: State()
    data class Error(val message: String): State()
    data class Success(val noteList: List<NoteResponseItem>): State()
}