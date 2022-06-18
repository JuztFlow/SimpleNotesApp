package de.hka.esfl1011.oeha1016.simplenotesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import de.hka.esfl1011.oeha1016.simplenotesapp.theme.Purple500
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.SimpleNotesAppViewModel

@Composable
fun DetailNoteScreen(notesViewModel: SimpleNotesAppViewModel, onDeleteNote: (note: Note) -> Unit) {

    val uiData by notesViewModel.notesUIData.collectAsState()

    DetailNoteScreen(uiData.isLoading, uiData.currentNote, onDeleteNote)
}

@Composable
private fun DetailNoteScreen(
    isLoading: Boolean,
    note: Note,
    onDeleteNote: (note: Note) -> Unit
) {
    if (isLoading) {
        LoadingContent()
    } else {
        DetailNoteContent(
            note = note,
            onDeleteNote = onDeleteNote
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DetailNoteContent(
    note: Note,
    onDeleteNote: (note: Note) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("Simple Notes - Detail") }, actions = {
                IconButton(
                    onClick = { onDeleteNote(note) },
                ) {
                    Icon(Icons.Filled.Delete, "Delete")
                }
            })
        },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Purple500, thickness = 2.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = note.description,
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp
                )
            }
        }
    )
}
