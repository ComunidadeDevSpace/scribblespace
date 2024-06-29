package com.devspace.scribblespace.create.presentation.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.devspace.scribblespace.create.presentation.CreateNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel,
    navController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    if (uiState.isFailed) {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Note") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            CreateNoteScreenContent(
                modifier = Modifier
                    .padding(paddingValues),
                isLoading = uiState.isLoading,
                isSuccess = uiState.isSuccess,
                navigateBack = {
                    // PopBack to specific route avoiding blank screen
                    navController.popBackStack(route = "noteList", inclusive = false)
                },
                onCreateClicked = { (title, description) ->
                    viewModel.addNote(title, description)
                }
            )
        }
    )

}

@Composable
fun CreateNoteScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isSuccess: Boolean,
    navigateBack: () -> Unit,
    onCreateClicked: (Pair<String, String>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else if (isSuccess) {
        navigateBack.invoke()
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Title",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Transparent,
                        unfocusedContainerColor = Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Description",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Transparent,
                        unfocusedContainerColor = Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Button(
                onClick = {
                    onCreateClicked.invoke(title to description)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Save Note")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewCreateNoteScreen() {
    CreateNoteScreenContent(
        isLoading = false,
        isSuccess = false,
        navigateBack = {},
        onCreateClicked = {}
    )
}