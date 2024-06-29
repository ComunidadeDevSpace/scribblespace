package com.devspace.scribblespace

import android.app.Application
import androidx.room.Room
import com.devspace.scribblespace.common.local.ScribbleSpaceDataBase

class ScribbleSpaceApplication : Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ScribbleSpaceDataBase::class.java, "database-scribble-space-app"
        ).build()
    }

}