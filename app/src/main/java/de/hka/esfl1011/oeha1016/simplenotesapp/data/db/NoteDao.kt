package de.hka.esfl1011.oeha1016.simplenotesapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * FROM note ORDER BY created_date_time DESC")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note where id = :id")
    suspend fun getNote(id: Long): Note

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
