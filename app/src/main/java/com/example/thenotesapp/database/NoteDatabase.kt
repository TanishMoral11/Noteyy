// Package declaration, defines the namespace for the class
package com.example.thenotesapp.database

// Import statement to include Context class, used to access application-specific resources and classes
import android.content.Context

// Import statement to include Room's Database annotation, which marks this class as a Room database
import androidx.room.Database

// Import statement to include Room's Room class, which provides methods for creating Room databases
import androidx.room.Room

// Import statement to include Room's RoomDatabase class, which is the base class for Room databases
import androidx.room.RoomDatabase

// Import the Note class from the model package
import com.example.thenotesapp.model.Note

// Database annotation to mark this class as a Room database with Note entity and version 1
@Database(entities = [Note::class], version = 1)

// Abstract class for the Room database, extending RoomDatabase
abstract class NoteDatabase : RoomDatabase() {

    // Abstract function to get the NoteDao
    abstract fun getNoteDao(): NoteDao

    // Companion object to hold the singleton instance of the database
    companion object {
        // Volatile annotation to ensure the value of instance is always up-to-date and visible to all threads
        @Volatile
        private var instance: NoteDatabase? = null

        // Lock object for synchronizing instance creation
        private val LOCK = Any()

        // Invoke operator to provide the instance of the database
        // If instance is not null, return it, otherwise create a new database instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        // Function to create the Room database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }
}
