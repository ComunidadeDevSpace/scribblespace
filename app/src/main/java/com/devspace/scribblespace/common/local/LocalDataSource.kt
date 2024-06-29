package com.devspace.scribblespace.common.local

import android.app.Application
import androidx.compose.ui.util.fastMap
import com.devspace.scribblespace.ScribbleSpaceApplication
import com.devspace.scribblespace.common.model.NoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class LocalDataSource private constructor(
    private val dao: NoteDao
) {

    fun getAllNotes(): Flow<List<NoteData>> {
        return dao
            .getAll()
            .map { entities ->
                entities
                    .toList()
                    .map {
                        NoteData(
                            key = it.key,
                            title = it.title,
                            description = it.description
                        )
                    }
            }
    }

    suspend fun insert(noteEntity: NoteEntity) = dao.insert(noteEntity)

    suspend fun deleteById(id: String) = dao.deleteById(id)

    companion object {

        fun create(application: Application): LocalDataSource {
            val db = (application as ScribbleSpaceApplication).db
            val dao = db.getNoteDao()
            return LocalDataSource(dao = dao)
        }
    }
}