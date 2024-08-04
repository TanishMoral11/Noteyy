// Package declaration, defines the namespace for the class
package com.example.thenotesapp.database

// Import LiveData from Android Architecture Components for observable data holder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Import Room's Dao annotation, which marks the interface as a Data Access Object
import androidx.room.Dao

// Import Room's Delete annotation, which marks the method as a delete operation
import androidx.room.Delete

// Import Room's Insert annotation, which marks the method as an insert operation
import androidx.room.Insert

// Import Room's OnConflictStrategy to handle conflicts in the database
import androidx.room.OnConflictStrategy

// Import Room's Query annotation, which marks the method as a query operation
import androidx.room.Query

// Import Room's Update annotation, which marks the method as an update operation
import androidx.room.Update

// Import the Note class from the model package
import com.example.thenotesapp.model.Note

// Dao annotation to define the NoteDao interface as a Data Access Object for the Note entity
@Dao
interface NoteDao {

    // Insert annotation to insert a new note into the database
    // onConflict = OnConflictStrategy.REPLACE means that if there is a conflict, the existing note will be replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Update annotation to update an existing note in the database
    @Update
    suspend fun updateNote(note: Note)

    // Delete annotation to delete a note from the database
    @Delete
    suspend fun deleteNote(note: Note)

    // Query annotation to fetch all notes from the database, ordered by id in descending order
    // Returns LiveData to observe the data changes
    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // Query annotation to search notes by title or description
    // The query uses LIKE operator to match the search pattern
    // Returns LiveData to observe the search results
    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteDesc LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>{
        return if (query.isNullOrEmpty()){
            MutableLiveData(emptyList())
        }

        else {
            getAllNotes()
        }
    }
}
