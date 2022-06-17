package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import de.hka.esfl1011.oeha1016.simplenotesapp.theme.Purple500
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.state.ListNoteScreenState
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.state.rememberListNoteScreenState
import java.time.OffsetDateTime

@Composable
fun ListNoteScreen(notesViewModel: SimpleNotesAppViewModel, onItemClicked: (item: Note) -> Unit) {

    val uiData by notesViewModel.notesUIData.collectAsState()
    val newNoteTextStateHolder = rememberListNoteScreenState()

    ListNoteScreen(
        notes = uiData.notes,
        isLoading = uiData.isLoading,
        onItemClicked = onItemClicked,
        newNoteTextStateHolder,
        onCreateNewNote = {
            notesViewModel.addNote(
                Note(
                    0,
                    OffsetDateTime.now(),
                    newNoteTextStateHolder.titleState,
                    newNoteTextStateHolder.descriptionState
                )
            )
        }
    )
}

@Composable
private fun ListNoteScreen(
    notes: List<Note>,
    isLoading: Boolean,
    onItemClicked: (item: Note) -> Unit,
    newNoteTextStateHolder: ListNoteScreenState,
    onCreateNewNote: () -> Unit
) {
    if (isLoading) {
        LoadingContent()
    } else {
        NoteContent(
            notes = notes,
            onItemClicked = onItemClicked,
            newNoteTextStateHolder = newNoteTextStateHolder,
            onCreateNewNote = onCreateNewNote
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
private fun NoteContent(
    notes: List<Note>,
    onItemClicked: (item: Note) -> Unit,
    newNoteTextStateHolder: ListNoteScreenState,
    onCreateNewNote: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("Simple Notes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = !showDialog }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_input_add),
                    contentDescription = "Create new Note"
                )
            }
        },
        content = {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    properties = DialogProperties(
                        dismissOnClickOutside = true,
                        dismissOnBackPress = true
                    ),
                    modifier = Modifier.wrapContentHeight(),
                    buttons = {
                        LazyRow(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            item {
                                Button(
                                    onClick = { showDialog = false }
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                            item {
                                Button(
                                    onClick = onCreateNewNote
                                ) {
                                    Text(text = "Create")
                                }
                            }
                        }
                    },
                    title = {
                        Text(text = "Create new Note:")
                        //TODO: Spacing below
                    },
                    text = {
                        Column {

                            OutlinedTextField(
                                value = newNoteTextStateHolder.titleState,
                                onValueChange = { newText -> newNoteTextStateHolder.titleState = newText },
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = newNoteTextStateHolder.descriptionState,
                                onValueChange = { newText -> newNoteTextStateHolder.descriptionState = newText },
                                singleLine = false,
                                maxLines = 5
                            )
                        }
                    }
                )
            }

            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(notes) { item ->
                        NoteListElement(note = item, onItemClicked = onItemClicked)
                    }
                }
            }
        }
    )
}

@Composable
fun NoteListElement(note: Note, onItemClicked: (item: Note) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClicked(note) }
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Purple500, thickness = 2.dp)
        }
    }
}
