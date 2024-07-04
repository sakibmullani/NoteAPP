package com.example.thenoteapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thenoteapp.MainActivity
import com.example.thenoteapp.R
import com.example.thenoteapp.adapter.NoteAdapter
import com.example.thenoteapp.database.DeleteNote
import com.example.thenoteapp.databinding.FragmentHomeBinding
import com.example.thenoteapp.model.Note
import com.example.thenoteapp.viewModel.NoteViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,MenuProvider,DeleteNote {


    private var homeBinding: FragmentHomeBinding? =null
    private  val binding get() = homeBinding!!

    private  lateinit var notesViewModel :NoteViewModel
    private lateinit var  noteAdapert : NoteAdapter
    private lateinit var currentNote: Note


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        homeBinding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost= requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).viewModel

        setRecyclerView()
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private  fun updateUI(note: List<Note>?){
        if (note != null){
            if (note.isNotEmpty()){
                binding.emptyNotesImage.visibility=View.GONE
                binding.homeRecyclerView.visibility=View.VISIBLE
            }else{
                binding.emptyNotesImage.visibility=View.VISIBLE
                binding.homeRecyclerView.visibility=View.GONE
            }
        }
    }

    private  fun setRecyclerView(){
        noteAdapert= NoteAdapter(this)
        binding.homeRecyclerView.apply {
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter=noteAdapert

        }

        activity?.let {
            notesViewModel.getAllNotes().observe(viewLifecycleOwner){note ->
                noteAdapert.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun searchNote(query: String?){
        val serchQuery ="%$query"

        notesViewModel.searchNotes(serchQuery).observe(this){list ->
            noteAdapert.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchNote(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled=false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  false
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }

    private fun deleteNoteData(note: Note){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete note")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(note)
                Toast.makeText(context,"Note Delete", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun deleteNote(note: Note) {
        deleteNoteData(note)
    }

}