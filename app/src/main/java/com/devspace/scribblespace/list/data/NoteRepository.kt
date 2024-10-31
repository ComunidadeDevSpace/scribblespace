package com.devspace.scribblespace.list.data

import android.app.Application
import com.devspace.scribblespace.common.local.LocalDataSource
import com.devspace.scribblespace.common.local.NoteEntity
import com.devspace.scribblespace.common.model.NoteData
import com.devspace.scribblespace.common.remote.RemoteDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class NoteRepository private constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    fun getNoteList(): Flow<List<NoteData>> = flow {
        emitAll(local.getAllNotes())
    }
        .onStart {
            val notesResultFromRemote = remote.getNotes()
            if (notesResultFromRemote.isSuccess) {
                val notesFromRemote = notesResultFromRemote.getOrNull()
                notesFromRemote?.forEach { note ->
                    val entity = NoteEntity(
                        key = note.key,
                        title = note.title,
                        description = note.description
                    )
                    local.insert(entity)
                }
            }
        }

    suspend fun deleteNote(id: String): Result<Unit> {
        val result = remote.deleteNote(id)
        if (result.isSuccess) {
            local.deleteById(id)
        }

        return result
    }

    companion object {
        fun create(application: Application): NoteRepository {
            return NoteRepository(
                local = LocalDataSource.create(application),
                remote = RemoteDataSource.create()
            )
        }
    }
}