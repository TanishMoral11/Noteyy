package com.example.thenotesapp.fragments

import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thenotesapp.MainActivity
import com.example.thenotesapp.R
import com.example.thenotesapp.adapter.NoteAdapter
import com.example.thenotesapp.model.Note
import com.example.thenotesapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) , SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var notesViewModel : NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
       homeBinding  = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel

        binding.addNoteFab.setOnClickListener{
            it.findNavController()).navigate(R.id.action_homeFragent_to_addNoteFragment)        }
    }

    private fun updateUI(note : List<Note>?) {
        if(note!=null){
            if(note.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.emptyRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.emptyRecyclerview.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView() {
        noteAdapter = noteAdapter()
        binding.homeRecyclerView.apply {
          layoutManager = StraggerdGridLayoutManager(2, StaggeredGridLayoutManager..VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter

        }

        activity?.let {
            notesViewModel.getAllNote().observe(viewLifecycleOwner){
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
    }


}

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}