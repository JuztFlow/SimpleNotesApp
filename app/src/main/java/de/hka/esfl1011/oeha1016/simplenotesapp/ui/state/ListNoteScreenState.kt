package de.hka.esfl1011.oeha1016.simplenotesapp.ui.state

import androidx.compose.runtime.*

class ListNoteScreenState {
    var titleState: String by mutableStateOf("")
    var descriptionState: String by mutableStateOf("")
    var showDialogState: Boolean by mutableStateOf(false)
}

@Composable
fun rememberListNoteScreenState() = remember { ListNoteScreenState() }
