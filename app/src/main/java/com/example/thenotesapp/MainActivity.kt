package com.example.thenotesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thenotesapp.database.NoteDatabase
import com.example.thenotesapp.repository.NoteRepository
import com.example.thenotesapp.viewmodel.NoteViewModel
import com.example.thenotesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var notesViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        val noteRepository = NoteRepository(NoteDatabase.getDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        notesViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
        Log.d("MainActivity", "ViewModel initialized: $notesViewModel")
    }
}
