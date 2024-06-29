package com.devspace.scribblespace.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.scribblespace.create.data.CreateNoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNoteViewModel private constructor(
    private val repository: CreateNoteRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateNoteUiState())
    val uiState: StateFlow<CreateNoteUiState> = _uiState.asStateFlow()


    fun addNote(title: String, description: String) {
        _uiState.value = CreateNoteUiState(isLoading = true)

        viewModelScope.launch(dispatcher) {
            val result = repository.addNote(
                title = title,
                description = description
            )

            if (result.isSuccess) {
                _uiState.value = CreateNoteUiState(isSuccess = true)

                // Clean the state after 1sec given VM instance still alive, by using hilt should fix this
                delay(1000)
                _uiState.value = CreateNoteUiState()

            } else {
                _uiState.value = CreateNoteUiState(isFailed = true)
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return CreateNoteViewModel(
                    repository = CreateNoteRepository.create(application),
                    dispatcher = Dispatchers.IO
                ) as T
            }
        }
    }

}

data class CreateNoteUiState(
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
    val isSuccess: Boolean = false
)