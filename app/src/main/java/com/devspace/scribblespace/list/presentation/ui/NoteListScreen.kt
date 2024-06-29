package com.devspace.scribblespace.list.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devspace.scribblespace.R
import com.devspace.scribblespace.list.presentation.NoteListViewModel
import com.devspace.scribblespace.ui.theme.ScribbleSpaceTheme

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel,
    navController: NavController
) {

    val notes by viewModel.uiNotes.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        if (notes.isEmpty()) {
            EmptyState {
                navController.navigate("createNote")
            }
        } else {
            NoteListContent(
                notes = notes,
                onDelete = { item ->
                    viewModel.deleteNote(item)
                }
            )
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    navController.navigate("createNote")
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }

    }
}

@Composable
private fun NoteListContent(
    modifier: Modifier = Modifier,
    notes: List<NoteUiData>,
    onDelete: (NoteUiData) -> Unit,
) {

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            text = "ScribbleSpace"
        )

        LazyColumn {
            items(notes) { note ->
                NoteItem(note = note, onDelete = onDelete)
            }
        }
    }
}

@Composable
private fun EmptyState(
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            text = "ScribbleSpace"
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = painterResource(
                id = R.drawable.ic_notes
            ),
            contentDescription = "Notes Empty icon"
        )
        Button(onClick = onClick) {
            Text(text = "Start now")
        }
    }
}

@Composable
private fun NoteItem(
    note: NoteUiData,
    onDelete: (NoteUiData) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    text = note.title
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Normal,
                    text = note.description
                )
            }
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onDelete.invoke(note)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete button"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteList() {
    ScribbleSpaceTheme {
        /* val notes = listOf(NoteUiData("9", "note title", "note description"))
         NoteListContent(notes = notes) {

         }*/

        EmptyState() {

        }
    }
}