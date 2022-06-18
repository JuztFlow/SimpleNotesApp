package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.screens.DetailNoteScreen
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.screens.ListNoteScreen

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
        composable(Route.ListNoteRoute.route) {
            ListNoteScreen(notesViewModel) { note ->
                notesViewModel.updateCurrentNote(note)
                navController.navigate(Route.DetailNoteRoute.createRoute(note.id))
            }
        }
        composable(
            Route.DetailNoteRoute.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            DetailNoteScreen(notesViewModel) { note ->
                notesViewModel.removeNote(note)
                navController.popBackStack()
            }
        }
    }
}
