package de.hka.esfl1011.oeha1016.simplenotesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
            ListNoteScreen(notesViewModel) {
                    noteItem -> navController.navigate(Route.DetailNoteRoute.createRoute(noteItem.id))
            }
        }

        composable(
            Route.DetailNoteRoute.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { navBackStackEntry ->
            DetailNoteScreen(navBackStackEntry.arguments?.getLong("id") ?: 0) {
                navController.popBackStack()
            }
        }
    }
}
