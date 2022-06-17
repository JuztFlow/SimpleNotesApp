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

    private fun loadNotes() {
        _notesUIData.update { prev -> prev.copy(isLoading = true) }
        viewModelScope.launch {
            val notes = notesRepository.getNotes()
            _notesUIData.update { prev -> prev.copy(isLoading = false, notes = notes) }
        }
    }

    fun getNote(id: Long) {
        viewModelScope.launch {
            notesRepository.getNote(id)
        }
    }

    fun addNote(note: Note) {
        _notesUIData.update { prev -> prev.copy(isLoading = true) }
        viewModelScope.launch {
            notesRepository.addNote(note)
            _notesUIData.update { prev -> prev.copy(isLoading = false, notes = listOf(note) + prev.notes ) }
        }
    }

    fun removeNote(note: Note) {
        viewModelScope.launch {
            notesRepository.removeNote(note)
        }
    }

    companion object {
        private val DEFAULT_NOTE_UI_STATE = NotesUIData(emptyList(), false)
    }
}
