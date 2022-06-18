package de.hka.esfl1011.oeha1016.simplenotesapp.data

import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun getNotes(): List<Note> = withContext(Dispatchers.IO) {
        return@withContext noteDao.getAll()
    }

    suspend fun addNote(note: Note) = withContext(Dispatchers.IO) {
        noteDao.insert(note)
    }

    suspend fun removeNote(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }
}
