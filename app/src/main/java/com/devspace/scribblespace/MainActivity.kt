package com.devspace.scribblespace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devspace.scribblespace.create.presentation.CreateNoteViewModel
import com.devspace.scribblespace.list.presentation.NoteListViewModel
import com.devspace.scribblespace.ui.theme.ScribbleSpaceTheme
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MainActivity : ComponentActivity() {

    private val noteListViewModel: NoteListViewModel by viewModels<NoteListViewModel> {
        NoteListViewModel.factory
    }

    private val createNoteViewModel: CreateNoteViewModel by viewModels<CreateNoteViewModel> {
        CreateNoteViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)

        setContent {
            ScribbleSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScribbleSpaceApp(
                        noteListViewModel = noteListViewModel,
                        createNoteViewModel = createNoteViewModel
                    )
                }
            }
        }
    }
}
