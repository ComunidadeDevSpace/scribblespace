package com.devspace.scribblespace

import app.cash.turbine.test
import com.devspace.scribblespace.common.model.NoteData
import com.devspace.scribblespace.list.data.NoteRepository
import com.devspace.scribblespace.list.presentation.NoteListViewModel
import com.devspace.scribblespace.list.presentation.ui.NoteUiData
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


class NoteListViewModelTest {

    private val repository: NoteRepository = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private val underTest: NoteListViewModel by lazy {
        NoteListViewModel(
            repository = repository,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `GIVEN empty list When fetching notes Then return empty initially`() {
        runTest {
            // Given
            whenever(repository.getNoteList()).doReturn(flowOf(emptyList()))

            //When
            underTest.uiNotes.test {
                assert(
                    emptyList<NoteData>() == awaitItem()
                )
            }
        }
    }

    @Test
    fun `GIVEN data list When fetching notes Then return empty initially`() {
        runTest {
            //Given
            val notes = listOf(
                NoteData(
                    key = "key1",
                    title = "title1",
                    description = "description1"
                )
            )

            whenever(repository.getNoteList()).doReturn(flowOf(notes))

            //When
            underTest.uiNotes.test {
                val expected = listOf(
                    NoteUiData(
                        id = "key1",
                        title = "title1",
                        description = "description1"
                    )
                )
                assert(
                    expected == awaitItem()
                )
            }
        }
    }

}