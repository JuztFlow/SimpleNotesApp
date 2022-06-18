package de.hka.esfl1011.oeha1016.simplenotesapp.ui.screens

import android.R
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.hka.esfl1011.oeha1016.simplenotesapp.data.db.Note
import de.hka.esfl1011.oeha1016.simplenotesapp.theme.Purple500
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.SimpleNotesAppViewModel
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.state.ListNoteScreenState
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.state.rememberListNoteScreenState
import kotlinx.coroutines.launch


@Composable
fun ListNoteScreen(notesViewModel: SimpleNotesAppViewModel, onItemClicked: (item: Note) -> Unit) {

    val uiData by notesViewModel.notesUIData.collectAsState()
    val listNoteScreenStateHolder = rememberListNoteScreenState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    ListNoteScreen(
        notes = uiData.notes,
        isLoading = uiData.isLoading,
        onItemClicked = onItemClicked,
        listNoteScreenStateHolder,
        onCreateNewNote = {
            when {
                listNoteScreenStateHolder.titleState.isEmpty() and listNoteScreenStateHolder.descriptionState.isNotEmpty() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Title cannot be empty!", Toast.LENGTH_SHORT).show()
                    }
                }
                listNoteScreenStateHolder.titleState.isNotEmpty() and listNoteScreenStateHolder.descriptionState.isEmpty() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Description cannot be empty!", Toast.LENGTH_SHORT).show()
                    }
                }
                listNoteScreenStateHolder.titleState.isBlank() and listNoteScreenStateHolder.descriptionState.isNotBlank() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Title cannot be blank!", Toast.LENGTH_SHORT).show()
                    }
                }
                listNoteScreenStateHolder.titleState.isNotBlank() and listNoteScreenStateHolder.descriptionState.isBlank() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Description cannot be blank!", Toast.LENGTH_SHORT).show()
                    }
                }
                listNoteScreenStateHolder.titleState.isEmpty() and listNoteScreenStateHolder.descriptionState.isEmpty() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Title & Description cannot be empty!", Toast.LENGTH_SHORT).show()
                    }
                }
                listNoteScreenStateHolder.titleState.isBlank() and listNoteScreenStateHolder.descriptionState.isBlank() -> {
                    coroutineScope.launch {
                        Toast.makeText(context, "Title & Description cannot be blank!", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    notesViewModel.addNote(
                        listNoteScreenStateHolder.titleState,
                        listNoteScreenStateHolder.descriptionState
                    )
                    listNoteScreenStateHolder.titleState = ""
                    listNoteScreenStateHolder.descriptionState = ""
                    listNoteScreenStateHolder.showDialogState = false
                }
            }
        }
    )
}

@Composable
private fun ListNoteScreen(
    notes: List<Note>,
    isLoading: Boolean,
    onItemClicked: (item: Note) -> Unit,
    listNoteScreenStateHolder: ListNoteScreenState,
    onCreateNewNote: () -> Unit,
) {
    if (isLoading) {
        LoadingContent()
    } else {
        NoteContent(
            notes = notes,
            onItemClicked = onItemClicked,
            listNoteScreenStateHolder = listNoteScreenStateHolder,
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
    listNoteScreenStateHolder: ListNoteScreenState,
    onCreateNewNote: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("Simple Notes - Overview") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { listNoteScreenStateHolder.showDialogState = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_input_add),
                    contentDescription = "Create new Note"
                )
            }
        },
        content = {
            if (listNoteScreenStateHolder.showDialogState) {
                CreateNewNoteAlertDialog(
                    listNoteScreenStateHolder = listNoteScreenStateHolder,
                    onCreateNewNote = onCreateNewNote
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
fun CreateNewNoteAlertDialog(
    listNoteScreenStateHolder: ListNoteScreenState,
    onCreateNewNote: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            listNoteScreenStateHolder.showDialogState = false
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        content = {
            Surface {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 6.dp, bottom = 16.dp),
                        fontSize = 18.sp,
                        text = "Create new Note:"
                    )
                    Column(
                        modifier = Modifier.height(280.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Title")
                            },
                            placeholder = {
                                Text(text = "Your Title...", color = Color.Gray)
                            },
                            value = listNoteScreenStateHolder.titleState,
                            onValueChange = { newText -> listNoteScreenStateHolder.titleState = newText },
                            singleLine = true
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            label = {
                                Text(text = "Description")
                            },
                            placeholder = {
                                Text(text = "Some Description...", color = Color.Gray)
                            },
                            value = listNoteScreenStateHolder.descriptionState,
                            onValueChange = { newText -> listNoteScreenStateHolder.descriptionState = newText },
                            singleLine = false,
                            maxLines = 6
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            item {
                                Button(
                                    modifier = Modifier.width(130.dp),
                                    onClick = {
                                        listNoteScreenStateHolder.titleState = ""
                                        listNoteScreenStateHolder.descriptionState = ""
                                        listNoteScreenStateHolder.showDialogState = false
                                    }
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                            item {
                                Button(
                                    modifier = Modifier.width(130.dp),
                                    onClick = onCreateNewNote
                                ) {
                                    Text(text = "Create")
                                }
                            }
                        }
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
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = note.title,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Purple500, thickness = 2.dp)
        }
    }
}
