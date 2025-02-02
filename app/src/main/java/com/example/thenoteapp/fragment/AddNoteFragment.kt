package com.example.thenoteapp.fragment

import android.graphics.Color
import android.os.Bundle
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
import com.example.thenoteapp.MainActivity
import com.example.thenoteapp.R
import com.example.thenoteapp.databinding.FragmentAddNoteBinding
import com.example.thenoteapp.model.Note
import com.example.thenoteapp.viewModel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {

    private  var addNoteBinding: FragmentAddNoteBinding? =null
    private val binding get() = addNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView : View
    var colorCode : String = "#FFFFFFFF"
    var flag=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding=FragmentAddNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).viewModel
        addNoteView= view


        binding.setWhite.setOnClickListener {
            colorCode = "#FFFFFFFF"
            val color = Color.parseColor(colorCode)
            binding.mainColorSetLayout.setBackgroundColor(color)
        }
        binding.setYellow.setOnClickListener {
            colorCode = "#F4E34A"
            val color = Color.parseColor(colorCode)
            binding.mainColorSetLayout.setBackgroundColor(color)
        }
        binding.setBlue.setOnClickListener {
            colorCode = "#F3764E"
            val color = Color.parseColor(colorCode)
            binding.mainColorSetLayout.setBackgroundColor(color)
        }
    }

    private fun saveNote(view: View){
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()

        if (noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteDesc,colorCode)
            notesViewModel.addNote(note)

            Toast.makeText(addNoteView.context,"Note Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)

        }else{
            Toast.makeText(addNoteView.context,"Please Enter Note title",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_note_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                flag=1
                saveNote(addNoteView)
                true
            }
            else->false

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }

    override fun onStop() {
        super.onStop()
        if (flag==0){
            saveNote(addNoteView)
        }

    }



}