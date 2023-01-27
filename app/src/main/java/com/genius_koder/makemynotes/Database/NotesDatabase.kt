package com.genius_koder.makemynotes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.genius_koder.makemynotes.Dao.NotesDao
import com.genius_koder.makemynotes.Model.Notes
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

// func inside abstract class do not have body
@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    // used to access dao
    abstract fun myNotesDao() : NotesDao

    companion object{

        // Volatile annotation means it can be accessed easily
        @Volatile
        var INSTANCE : NotesDatabase?=null

        @Synchronized
        fun getDatabaseInstance(context: Context) : NotesDatabase {
            val tempInstance = INSTANCE
            if(tempInstance!=null) {
                // room database instance has been built
                return tempInstance
            }
            // Instance is not formed in that case we build room database via synchronized
                val roomDatabaseInstance =
                    Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        "Notes").allowMainThreadQueries().build()
                // allowMainThreadQueries makes main thread free to perform any activity
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
        }
    }

}