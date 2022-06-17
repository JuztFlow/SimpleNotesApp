package de.hka.esfl1011.oeha1016.simplenotesapp.ui

sealed class Route(val route: String) {
    object ListNoteRoute : Route("notes")
    object DetailNoteRoute : Route("notes/{id}") {
        fun createRoute(id: Long) = "notes/$id"
    }
}
