package com.example.thenotesapp.database

import androidx.room.Database
import com.example.thenotesapp.model.Note


@Database(entities = [Note::class] , version = 1)
class NoteDatabase {
}