package com.genius_koder.makemynotes.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.genius_koder.makemynotes.Model.Notes
import com.genius_koder.makemynotes.R
import com.genius_koder.makemynotes.databinding.ItemNotesBinding
import com.genius_koder.makemynotes.ui.Adapter.NotesAdapter.*
import com.genius_koder.makemynotes.ui.Fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context,var notesList: List<Notes>) :
    RecyclerView.Adapter<notesViewHolder>(){

    fun filteringNotes(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }

    class notesViewHolder (val binding : ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    // returns the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.notesTitle.text = data.title
        holder.binding.notesSubtitle.text = data.subTitle
        holder.binding.notesDate.text = data.date

        when(data.priority) {
            "1"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
            "2"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }
        }

        holder.binding.root.setOnClickListener{

        val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment()
        Navigation.findNavController(it).navigate(action)

        }
    }

    // returns the size of Notes list
    override fun getItemCount(): Int {
        //Log.e("@@@@&","${notesList.size}")
        return notesList.size
    }

}