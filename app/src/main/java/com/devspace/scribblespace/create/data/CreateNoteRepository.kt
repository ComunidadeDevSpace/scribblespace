package com.devspace.scribblespace.create.data

import android.app.Application
import com.devspace.scribblespace.common.local.LocalDataSource
import com.devspace.scribblespace.common.local.NoteEntity
import com.devspace.scribblespace.common.remote.RemoteDataSource

class CreateNoteRepository private constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun addNote(title: String, description: String): Result<String> {
        val result = remote.addNote(
            title = title,
            description = description
        )

        if (result.isSuccess) {
            val key = result.getOrNull()
            if (key != null) {
                local.insert(
                    NoteEntity(
                        key = key,
                        title = title,
                        description = description
                    )
                )
            }
        }

        return result
    }

    companion object {
        fun create(application: Application): CreateNoteRepository {
            return CreateNoteRepository(
                local = LocalDataSource.create(application),
                remote = RemoteDataSource.create()
            )
        }
    }
}