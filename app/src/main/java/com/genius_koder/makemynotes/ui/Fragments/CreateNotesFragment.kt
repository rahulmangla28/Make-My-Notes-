package com.genius_koder.makemynotes.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.genius_koder.makemynotes.Model.Notes
import com.genius_koder.makemynotes.R
import com.genius_koder.makemynotes.ViewModel.NotesViewModel
import com.genius_koder.makemynotes.databinding.FragmentCreateNotesBinding
import kotlinx.coroutines.InternalCoroutinesApi

class CreateNotesFragment : Fragment() {

    lateinit var binding : FragmentCreateNotesBinding
    var priority : String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        // setting default notes priority
        binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)

        // setting priority
        binding.pRed.setOnClickListener {
            priority = "1"
            binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
        }
        binding.pGreen.setOnClickListener {
            priority = "3"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }


        // on click save the notes
        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it : View) {
        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = java.util.Date()
        val notesDate : CharSequence = DateFormat.format("MMMM d, yyyy", d.time)

        val data = Notes(
            null,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate.toString(),
            priority = priority
        )

        viewModel.addNotes(data)

        Toast.makeText(activity,"Notes Created Successfully",Toast.LENGTH_SHORT).show();

        Navigation.findNavController(it).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }

}