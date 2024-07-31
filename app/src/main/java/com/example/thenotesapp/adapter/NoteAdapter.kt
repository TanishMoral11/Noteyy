// Package declaration, defines the namespace for the class
package com.example.thenotesapp.adapter

// Import statement to include LayoutInflater, used to instantiate layout XML files into their corresponding View objects
import android.view.LayoutInflater

// Import statement to include ViewGroup, which is a special view that can contain other views (called children)
import android.view.ViewGroup

// Import statement to include findNavController, used to navigate between destinations in a Navigation graph
import androidx.navigation.findNavController

// Import statement to include AsyncListDiffer, which provides a mechanism to compute the difference between two lists and output a list of update operations
import androidx.recyclerview.widget.AsyncListDiffer

// Import statement to include DiffUtil, which is a utility class used to calculate the difference between two lists
import androidx.recyclerview.widget.DiffUtil

// Import statement to include RecyclerView, which is a ViewGroup that contains views corresponding to your data
import androidx.recyclerview.widget.RecyclerView

// Import the NoteLayoutBinding class, which is generated from the layout XML file
import com.example.thenotesapp.databinding.NoteLayoutBinding

// Import the HomeFragmentDirections class, which is generated from the navigation graph to handle navigation actions
import com.example.thenotesapp.fragments.HomeFragmentDirections

// Import the Note class from the model package
import com.example.thenotesapp.model.Note

// Define a NoteAdapter class that extends RecyclerView.Adapter with a nested NoteViewHolder class
class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Define a nested NoteViewHolder class that holds the view for each note item
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // Define a DiffUtil.ItemCallback for comparing Note objects
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        // Check if items represent the same Note based on their id, title, and description
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteDesc == newItem.noteDesc &&
                    oldItem.noteTitle == newItem.noteTitle
        }

        // Check if the content of items is the same
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    // Create an AsyncListDiffer with the differCallback to manage the list of notes
    val differ = AsyncListDiffer(this, differCallback)

    // Inflate the layout for the note item and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // Return the size of the current list of notes
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Get the current note from the list
        val currentNote = differ.currentList[position]

        // Set the title and description of the note in the ViewHolder
        holder.itemBinding.noteTitle.text = currentNote.noteTitle
        holder.itemBinding.noteDesc.text = currentNote.noteDesc

        // Set a click listener on the item view to navigate to the EditNoteFragment
        holder.itemView.setOnClickListener {
           val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            // Navigate to the EditNoteFragment with the current note as an argument
            it.findNavController().navigate(direction)
        }
    }
}
