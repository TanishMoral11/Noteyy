// Package declaration, defines the namespace for the class
package com.example.thenotesapp.viewmodel

// Import statement to include Application, which provides a base class for maintaining global application state

// Import statement to include AndroidViewModel, which is a ViewModel that is aware of the Application context
import android.app.Application
import androidx.lifecycle.AndroidViewModel

// Import statement to include viewModelScope, which is a CoroutineScope tied to the ViewModel's lifecycle
import androidx.lifecycle.viewModelScope
import com.example.thenotesapp.database.NoteDatabase

// Import the Note class from the model package
import com.example.thenotesapp.model.Note

// Import the NoteRepository class, which handles data operations for Note entities
import com.example.thenotesapp.repository.NoteRepository

// Import statement to include launch, which is used to start a new coroutine in viewModelScope
import kotlinx.coroutines.launch

// Define a NoteViewModel class that extends AndroidViewModel
// AndroidViewModel requires an Application instance, which is passed as app
class NoteViewModel(app: Application, private val noteRepository: NoteRepository) : AndroidViewModel(app) {

    // Function to add a note to the database
    // Runs the insertNote function from the repository in a coroutine using viewModelScope
    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    // Function to delete a note from the database
    // Runs the deleteNote function from the repository in a coroutine using viewModelScope
    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    // Function to update a note in the da tabase
    // Runs the updateNote function from the repository in a coroutine using viewModelScope
    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    // Function to get all notes from the database
    // Directly returns the result from the repository's getAllNotes function
    fun getAllNote() = noteRepository.getAllNotes()

    // Function to search notes in the database by a query string
    // Directly returns the result from the repository's searchNote function
    fun searchNote(query: String?) =
        noteRepository.searchNote(query)
}