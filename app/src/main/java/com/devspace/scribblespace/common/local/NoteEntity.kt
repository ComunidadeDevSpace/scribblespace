package com.devspace.scribblespace.common.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey()
    val key: String, // Remote key / id
    val title: String,
    val description: String
)