package com.techyourchance.architecture.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techyourchance.architecture.domain.question.FavoriteQuestion

@Database(
    entities = [
        FavoriteQuestion::class
    ],
    version = 1
)
internal abstract class MyRoomDatabase : RoomDatabase() {

    abstract val favoriteQuestionDao: FavoriteQuestionDao
}