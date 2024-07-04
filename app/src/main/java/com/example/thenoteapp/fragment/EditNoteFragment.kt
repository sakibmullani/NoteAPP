package com.example.thenoteapp.fragment

import android.app.AlertDialog
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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenoteapp.MainActivity
import com.example.thenoteapp.R
import com.example.thenoteapp.databinding.FragmentEditNoteBinding
import com.example.thenoteapp.model.Note
import com.example.thenoteapp.viewModel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {

    private  var editNoteBinding: FragmentEditNoteBinding?=null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote : Note

    private val args: EditNoteFragmentArgs by navArgs()
    var colorCode : String = "#FFFFFFFF"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         editNoteBinding=FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).viewModel
        currentNote =args.note!!

        binding.editNoteTitle.setText(currentNote.noteTital)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        val color = Color.parseColor(currentNote.colorCode)
        binding.mainColorSetLayout.setBackgroundColor(color)

        binding.editNoteFab.setOnClickListener{
            val notTital= binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if (notTital.isNotEmpty()){
                val note =Note(currentNote.id,notTital,noteDesc,colorCode)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }else{
                Toast.makeText(context,"Please Enter Note Title",Toast.LENGTH_SHORT).show()
            }
        }

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

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete note")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note Delete",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menu.clear()
        menuInflater.inflate(R.menu.edit_menu,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }

}