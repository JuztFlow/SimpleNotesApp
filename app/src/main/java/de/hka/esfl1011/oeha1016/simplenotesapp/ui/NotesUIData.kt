package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note

data class NotesUIData(
    val notes: List<Note>,
    val currentNote: Note,
    val isLoading: Boolean
)
