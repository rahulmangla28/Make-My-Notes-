package com.genius_koder.makemynotes.ui.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.genius_koder.makemynotes.Model.Notes
import com.genius_koder.makemynotes.R
import com.genius_koder.makemynotes.ViewModel.NotesViewModel
import com.genius_koder.makemynotes.databinding.FragmentHomeBinding
import com.genius_koder.makemynotes.ui.Adapter.NotesAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()
    var arrnotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager

        // we get all notes from NotesViewModel
        viewModel.getNotes().observe(viewLifecycleOwner, { notesList->
            arrnotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter = adapter

        })

        binding.allNotes.setOnClickListener{
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList->
                arrnotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.filterHigh.setOnClickListener{
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList->
                arrnotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.filterMedium.setOnClickListener{
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList->
                arrnotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.filterLow.setOnClickListener{
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList->
                arrnotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        fun onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) {
            inflater.inflate(R.menu.search_menu,menu)

            val item = menu.findItem(R.id.app_bar_search)
            val searchView = item.actionView as SearchView
            searchView.queryHint = "Search your notes here..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    NotesFiltering(p0)
                    return true
                }

                private fun NotesFiltering(p0: String?) {
                    val newFilteredList = arrayListOf<Notes>()
                    for( i in  arrnotes){
                        if(i.title.contains(p0!!) || i.subTitle.contains(p0!!)){
                            newFilteredList.add(i)
                        }
                    }
                    adapter.filteringNotes(newFilteredList)
                }
            })

            super.onCreateOptionsMenu(menu,inflater)
        }

        return binding.root
    }

}