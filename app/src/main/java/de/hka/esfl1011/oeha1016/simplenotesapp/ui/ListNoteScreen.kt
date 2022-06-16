package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController

@Composable
fun ListNoteScreen(navController: NavHostController, notesViewModel: SimpleNotesAppViewModel) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(text = "Hello from ListNoteScreen")
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Button(onClick = { navController.navigate(Route.NewNoteRoute.route) }) {
            Text(text = "To NewNoteScreen")
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Button(onClick = { navController.navigate(Route.DetailNoteRoute.route) }) {
            Text(text = "To DetailNoteScreen")
        }
    }
}
