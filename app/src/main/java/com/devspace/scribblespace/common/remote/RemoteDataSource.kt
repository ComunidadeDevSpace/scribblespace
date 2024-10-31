package com.devspace.scribblespace.common.remote

import com.devspace.scribblespace.common.model.NoteData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

private const val NOTE_COLLECTION = "notes"

class RemoteDataSource private constructor(

) {

    suspend fun addNote(title: String, description: String): Result<String> {
      return TODO()
    }

    suspend fun getNotes(): Result<List<NoteData>> {
       return TODO()
    }

    suspend fun deleteNote(id: String): Result<Unit> {
      return TODO()
    }

    companion object {
        fun create(): RemoteDataSource {
            return RemoteDataSource()
        }
    }
}