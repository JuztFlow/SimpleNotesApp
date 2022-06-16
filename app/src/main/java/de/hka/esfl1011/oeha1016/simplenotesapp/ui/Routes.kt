package de.hka.esfl1011.oeha1016.simplenotesapp.ui

sealed class Route(val route: String) {
    object ListNoteRoute : Route("list")
    object NewNoteRoute : Route("new")
    object DetailNoteRoute : Route("detail")
}
