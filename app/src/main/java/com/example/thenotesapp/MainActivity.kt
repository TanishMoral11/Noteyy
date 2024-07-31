// Package declaration, defines the namespace for the class
package com.example.thenotesapp

// Import statement to include Bundle, used to pass data between Android components and manage saved instance states
import android.os.Bundle

// Import statement to include AppCompatActivity, a base class for activities that use the support library action bar features
import androidx.appcompat.app.AppCompatActivity

// Import statement to include ViewModelProvider, which provides ViewModel instances and manages their lifecycle
import androidx.lifecycle.ViewModelProvider

// Import the NoteDatabase class, which handles database creation and version management using Room
import com.example.thenotesapp.database.NoteDatabase

// Import the NoteRepository class, which handles data operations for Note entities
import com.example.thenotesapp.repository.NoteRepository

// Import the NoteViewModel class, which holds the app's UI data and business logic
import com.example.thenotesapp.viewmodel.NoteViewModel

// Import the NoteViewModelFactory class, which is responsible for creating instances of NoteViewModel
import com.example.thenotesapp.viewmodel.NoteViewModelFactory

// Define the MainActivity class that extends AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Declare a late-initialized variable for NoteViewModel
    lateinit var noteViewModel: NoteViewModel

    // Override the onCreate method, which is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge() // Commented out, could be used for enabling edge-to-edge display on Android 10+

        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main)

        // Call the setupViewModel function to initialize the NoteViewModel
        setupViewModel()
    }

    // Private function to setup and initialize the NoteViewModel
    private fun setupViewModel() {
        // Create an instance of NoteRepository, passing the NoteDatabase created with the application context
        val noteRepository = NoteRepository(NoteDatabase(this))

        // Create an instance of NoteViewModelFactory, passing the application context and noteRepository
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        // Get an instance of NoteViewModel using ViewModelProvider and the factory
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}
