package com.example.thenoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.thenoteapp.database.NoteDatabase
import com.example.thenoteapp.repository.NoteRepo
import com.example.thenoteapp.viewModel.NoteViewModel
import com.example.thenoteapp.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViewModel()

    }

    private fun setupViewModel(){
        val noteRepo =NoteRepo(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application,noteRepo)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }
}