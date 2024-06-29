package com.devspace.scribblespace.common.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([NoteEntity::class], version = 1)
abstract class ScribbleSpaceDataBase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

}