package com.example.thenoteapp.database

import com.example.thenoteapp.model.Note

interface DeleteNote {

    fun deleteNote(note : Note)
}