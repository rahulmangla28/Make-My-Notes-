package com.genius_koder.makemynotes.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.genius_koder.makemynotes.Model.Notes

@Dao
interface NotesDao {

    // we get all notes from getNotes
    @Query("SELECT * FROM Notes")
    fun getNotes() : LiveData<List<Notes>>

    // we get all High notes from getHighNotes
    @Query("SELECT * FROM Notes WHERE priority=1")
    fun getHighNotes() : LiveData<List<Notes>>

    // we get all Medium notes from getMediumNotes
    @Query("SELECT * FROM Notes WHERE priority=2")
    fun getMediumNotes() : LiveData<List<Notes>>

    // we get all Low notes from getLowNotes
    @Query("SELECT * FROM Notes WHERE priority=3")
    fun getLowNotes() : LiveData<List<Notes>>

    // OnConflict the replaces the repeated query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes : Notes)

    // delete note of given id
    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes(id : Int)

    // updates the note
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotes(notes : Notes)
}