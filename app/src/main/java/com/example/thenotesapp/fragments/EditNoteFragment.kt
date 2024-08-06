package com.example.thenotesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenotesapp.MainActivity
import com.example.thenotesapp.R
import com.example.thenotesapp.databinding.FragmentEditNoteBinding
import com.example.thenotesapp.model.Note
import com.example.thenotesapp.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private var currentNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as? MainActivity)?.notesViewModel ?: run {
            Log.e("EditNoteFragment", "Error: Unable to initialize ViewModel")
            Toast.makeText(context, "Error: Unable to initialize ViewModel", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
            return
        }
        Log.d("EditNoteFragment", "ViewModel initialized: $notesViewModel")

        val note: Note? = arguments?.getParcelable("note")
        if (note != null) {
            currentNote = note
            binding.editNoteTitle.setText(note.noteTitle ?: "")
            binding.editNoteDesc.setText(note.noteDesc ?: "")
        } else {
            Log.e("EditNoteFragment", "Error: Note not found")
            Toast.makeText(context, "Error: Note not found", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
            return
        }

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if (noteTitle.isNotEmpty()) {
                currentNote?.let { note ->
                    val updatedNote = Note(note.id, noteTitle, noteDesc)
                    notesViewModel.updateNote(updatedNote)
                    view.findNavController().popBackStack(R.id.homeFragment, false)
                }
            } else {
                Toast.makeText(context, "Title is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                currentNote?.let { note ->
                    notesViewModel.deleteNote(note)
                    view?.findNavController()?.popBackStack(R.id.homeFragment, false)
                }
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editNoteBinding = null
    }
}
