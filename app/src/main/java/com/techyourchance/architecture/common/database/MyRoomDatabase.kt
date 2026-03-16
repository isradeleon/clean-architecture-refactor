package com.techyourchance.architecture.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.common.database.entities.FavoriteEntity

@Database(
    entities = [
        FavoriteEntity::class
    ],
    version = 1
)
internal abstract class MyRoomDatabase : RoomDatabase() {

    abstract val favoriteQuestionDao: FavoriteQuestionDao
}