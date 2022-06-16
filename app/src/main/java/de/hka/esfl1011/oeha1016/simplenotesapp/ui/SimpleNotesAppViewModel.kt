package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.hka.esfl1011.oeha1016.simplenotesapp.data.NotesRepository
import javax.inject.Inject

@HiltViewModel
class SimpleNotesAppViewModel @Inject constructor(
        private val notesRepository: NotesRepository
) : ViewModel() {

    init {
        Log.i(LOG_TAG, "Created")
    }

    override fun onCleared() {
        Log.i(LOG_TAG, "Cleared")
        super.onCleared()
    }

    companion object {
        const val LOG_TAG = "SimpleNotesAppViewModel"
    }
}
