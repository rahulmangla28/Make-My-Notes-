package com.genius_koder.makemynotes.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.genius_koder.makemynotes.Model.Notes
import com.genius_koder.makemynotes.R
import com.genius_koder.makemynotes.ViewModel.NotesViewModel
import com.genius_koder.makemynotes.databinding.FragmentCreateNotesBinding
import com.genius_koder.makemynotes.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.InternalCoroutinesApi

class EditNotesFragment : Fragment() {

    lateinit var binding : FragmentEditNotesBinding
    // notes holds the arguments from Home Fragment
    val oldNotes by navArgs<EditNotesFragmentArgs>()
    var priority : String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.title)
        binding.edtSubtitle.setText(oldNotes.subTitle)
        binding.edtNotes.setText(oldNotes.notes)

        when(oldNotes.priority) {
            "1"->{
                priority="1"
                binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2"->{
                priority="2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(0)
            }
            "3"->{
                priority="3"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
        }

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

        binding.btnEditSaveNotes.setOnClickListener{

            updateNotes(it)

        }

        return binding.root
    }

    private fun updateNotes(it : View) {
        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = java.util.Date()
        val notesDate : CharSequence = DateFormat.format("MMMM d, yyyy", d.time)

        val data = Notes(
            oldNotes.id,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate.toString(),
            priority = priority
        )
        viewModel.updateNotes(data)

        Navigation.findNavController(it).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            val bottomSheet : BottomSheetDialog
                = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialogue_delete)

            val dialogueYes = bottomSheet.findViewById<TextView>(R.id.dialogue_yes)
            val dialogueNo = bottomSheet.findViewById<TextView>(R.id.dialogue_no)

            dialogueYes?.setOnClickListener{
                viewModel.deleteNotes(oldNotes.id)
                bottomSheet.dismiss()
                // navigation might result in crash of app try it out once
                Navigation.findNavController(it).navigate(R.id.action_editNotesFragment_to_homeFragment)
            }
            dialogueNo?.setOnClickListener{
                bottomSheet.dismiss()
            }

            bottomSheet.show()

        }
        return super.onOptionsItemSelected(item)
    }

}