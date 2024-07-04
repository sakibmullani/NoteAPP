package com.example.thenoteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.thenoteapp.model.Note
import com.example.thenoteapp.repository.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(app: Application,private  val noteRepo: NoteRepo):AndroidViewModel(app) {

    fun addNote(note:Note)=
        viewModelScope.launch {
            noteRepo.insertNoote(note)
        }
    fun deleteNote(note:Note)=
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    fun updateNote(note:Note)=
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }
    fun getAllNotes()=noteRepo.getAllNotes()

    fun searchNotes(query: String?)=noteRepo.searchNote(query)

}