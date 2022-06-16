package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SimpleNotesAppNavigationGraph(
    notesViewModel: SimpleNotesAppViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.ListNoteRoute.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Route.ListNoteRoute.route) { ListNoteScreen(navController, notesViewModel) }
        composable(Route.NewNoteRoute.route) { NewNoteScreen(notesViewModel) }
        composable(Route.DetailNoteRoute.route) { DetailNoteScreen(notesViewModel) }
    }
}
