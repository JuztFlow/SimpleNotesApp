package de.hka.esfl1011.oeha1016.simplenotesapp.data

import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.NoteDao
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.NotesUIData
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.SimpleNotesAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun getNotes(): List<Note> = noteDao.getAll()

    suspend fun getNote(id: Long) = noteDao.getNote(id)

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun removeNote(note: Note) {
        noteDao.delete(note)
    }

}
