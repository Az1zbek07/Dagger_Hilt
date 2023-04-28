package com.example.daggerthilt.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerthilt.model.NoteResponseItem
import com.example.daggerthilt.repository.NoteRepository
import com.example.daggerthilt.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Empty)
    val state: StateFlow<State> get() = _state

    fun getAllNotes(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                repository.getRemoteNotes().onStart {
                    _state.update {
                        State.Loading
                    }
                }.catch {thro ->
                    _state.update {
                        State.Error(thro.message.toString())
                    }
                }.collect{list ->
                    _state.update {
                        State.Success(list)
                    }
                }
            }else{
                repository.getLocalNotes().onStart {
                    _state.update {
                        State.Loading
                    }
                }.collect{list ->
                    _state.update {
                        State.Success(list)
                    }
                }
                Toast.makeText(networkHelper.context, "These are your liked Notes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveNote(noteResponseItem: NoteResponseItem){
        viewModelScope.launch {
            repository.saveNote(noteResponseItem)
        }
    }
}