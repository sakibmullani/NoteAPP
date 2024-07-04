package com.example.thenoteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thenoteapp.repository.NoteRepo

class NoteViewModelFactory(val application: Application,private  val noterepo: NoteRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(application,noterepo) as T
    }
}