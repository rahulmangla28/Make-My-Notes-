package com.genius_koder.makemynotes.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.genius_koder.makemynotes.Dao.NotesDao
import com.genius_koder.makemynotes.Model.Notes

class NotesRepository (val dao : NotesDao){

    // fun used to get all notes from dao
    fun getAllNotes() : LiveData<List<Notes>> {
        return dao.getNotes()
    }

    // fun used to get all High notes from dao
    fun getHighNotes() : LiveData<List<Notes>> {
        return dao.getHighNotes()
    }

    // fun used to get all Medium notes from dao
    fun getMediumNotes() : LiveData<List<Notes>> {
        return dao.getMediumNotes()
    }

    // fun used to get all Low notes from dao
    fun getLowNotes() : LiveData<List<Notes>> {
        return dao.getLowNotes()
    }

    // fun used to insert notes of given id
    fun insertNotes(notes : Notes) {
        dao.insertNotes(notes)
    }

    // fun used to insert notes of given id
    fun deleteNotes(id : Int) {
        dao.deleteNotes(id)
    }

    // fun used to update notes
    fun updateNotes(notes : Notes) {
        dao.updateNotes(notes)
    }
}