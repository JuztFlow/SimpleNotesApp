package de.hka.esfl1011.oeha1016.simplenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hka.esfl1011.oeha1016.simplenotesapp.theme.SimpleNotesAppTheme
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.SimpleNotesAppNavigationGraph
import de.hka.esfl1011.oeha1016.simplenotesapp.ui.SimpleNotesAppViewModel

@AndroidEntryPoint
class SimpleNotesAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotesAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SimpleNotesApp()
                }
            }
        }
    }
}

@Composable
fun SimpleNotesApp() {
    val notesViewModel: SimpleNotesAppViewModel = hiltViewModel()
    SimpleNotesAppNavigationGraph(notesViewModel = notesViewModel)
}
