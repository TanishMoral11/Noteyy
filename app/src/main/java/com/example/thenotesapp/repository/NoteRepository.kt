package com.example.thenotesapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.example.thenotesapp.database.NoteDatabase
import com.example.thenotesapp.model.Note

class NoteRepository(private val db : NoteDatabase) {
    suspend fun insertNote(note : Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note : Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note : Note) = db.getNoteDao().updateNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = if (query.isNullOrEmpty()) {
        // Return empty LiveData if the query is null or empty
        MutableLiveData(emptyList())
    } else {
        db.getNoteDao().searchNote(query)
    }


}