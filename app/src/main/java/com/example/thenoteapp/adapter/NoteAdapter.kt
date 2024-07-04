package com.example.thenoteapp.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thenoteapp.R
import com.example.thenoteapp.database.DeleteNote
import com.example.thenoteapp.databinding.NoteLayoutItemBinding
import com.example.thenoteapp.fragment.HomeFragmentDirections
import com.example.thenoteapp.model.Note

class NoteAdapter(private val listener: DeleteNote): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteLayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = differ.currentList[position]

        holder.itemBinding.noteTitle.text=currentNote.noteTital
        holder.itemBinding.noteDesc.text=currentNote.noteDesc
        val colorCode = currentNote.colorCode
        val color = Color.parseColor(colorCode)
        holder.itemBinding.setColor.backgroundTintList = ColorStateList.valueOf(color)

        if (currentNote.colorCode.equals("#F3764E")){
            holder.itemBinding.noteDesc.setTextColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            val direction= HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }

        holder.itemView.setOnLongClickListener {
            listener.deleteNote(currentNote)
            true
        }

    }


    class NoteViewHolder (val itemBinding: NoteLayoutItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    private  val differCallBack =object : DiffUtil.ItemCallback<Note>(){

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id== newItem.id && oldItem.noteTital==newItem.noteTital && oldItem.noteDesc==newItem.noteDesc
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)
}

