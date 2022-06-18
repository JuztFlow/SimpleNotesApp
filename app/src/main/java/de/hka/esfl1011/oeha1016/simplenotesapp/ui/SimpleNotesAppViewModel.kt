package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hka.esfl1011.oeha1016.simplenotesapp.data.NotesRepository
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class SimpleNotesAppViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _notesUIData = MutableStateFlow(DEFAULT_NOTE_UI_STATE)
    val notesUIData = _notesUIData.asStateFlow()

    init {
        loadNotes()
    }

    private fun reloadNotes() {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notesUIData.update { prev -> prev.copy(isLoading = true) }
            val notes = notesRepository.getNotes()
            _notesUIData.update { prev -> prev.copy(isLoading = false, notes = notes) }
        }
    }

    fun updateCurrentNote(currentNote: Note) {
        _notesUIData.update { prev -> prev.copy(currentNote = currentNote) }
    }

    fun addNote(title: String, description: String) {
        viewModelScope.launch {
            _notesUIData.update { prev -> prev.copy(isLoading = true) }
            notesRepository.addNote(Note(0, OffsetDateTime.now(), title, description))
            reloadNotes()
        }
    }

    fun removeNote(note: Note) {
        viewModelScope.launch {
            _notesUIData.update { prev -> prev.copy(isLoading = true) }
            notesRepository.removeNote(note)
            _notesUIData.update { prev -> prev.copy(isLoading = false, notes = prev.notes.filter { it.id != note.id }) }
        }
    }

    companion object {
        private val DEFAULT_NOTE_UI_STATE = NotesUIData(
            emptyList(), Note(0, OffsetDateTime.MIN, "", ""), false
        )
    }
}
