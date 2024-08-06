package com.example.thenotesapp.repository

import androidx.lifecycle.LiveData
import com.example.thenotesapp.database.NoteDao
import com.example.thenotesapp.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun searchNote(query: String): LiveData<List<Note>> = noteDao.searchNote("%$query%")
}
