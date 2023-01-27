package com.genius_koder.makemynotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.genius_koder.makemynotes.Database.NotesDatabase
import com.genius_koder.makemynotes.Model.Notes
import com.genius_koder.makemynotes.Repository.NotesRepository
import kotlinx.coroutines.InternalCoroutinesApi

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository : NotesRepository

    // code inside it runs first
    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    // func used to add the note
    fun addNotes(notes : Notes) {
        repository.insertNotes(notes)
    }

    // func used to fetch the note
    fun getNotes() : LiveData<List<Notes>> {
        return repository.getAllNotes()
    }
    // func used to fetch the High note
    fun getHighNotes() : LiveData<List<Notes>> {
        return repository.getHighNotes()
    }
    // func used to fetch the Medium note
    fun getMediumNotes() : LiveData<List<Notes>> {
        return repository.getMediumNotes()
    }
    // func used to fetch the Low note
    fun getLowNotes() : LiveData<List<Notes>> {
        return repository.getLowNotes()
    }

    // func used to delete the note
    fun deleteNotes(id : Int) {
        repository.deleteNotes(id)
    }

    // func used to update the note
    fun updateNotes(notes : Notes) {
        repository.updateNotes(notes)
    }

}