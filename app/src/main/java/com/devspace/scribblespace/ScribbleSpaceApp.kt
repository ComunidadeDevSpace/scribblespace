package com.devspace.scribblespace

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devspace.scribblespace.create.presentation.CreateNoteViewModel
import com.devspace.scribblespace.create.presentation.ui.CreateNoteScreen
import com.devspace.scribblespace.list.presentation.ui.NoteListScreen
import com.devspace.scribblespace.list.presentation.NoteListViewModel

@Composable
fun ScribbleSpaceApp(
    noteListViewModel: NoteListViewModel,
    createNoteViewModel: CreateNoteViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "noteList") {
        composable(route = "noteList") {
            NoteListScreen(
                viewModel = noteListViewModel,
                navController = navController
            )
        }

        composable(route = "createNote") {
            CreateNoteScreen(
                viewModel = createNoteViewModel,
                navController = navController,
            )
        }

    }
}