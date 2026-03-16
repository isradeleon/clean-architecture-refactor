package com.techyourchance.architecture.ui.favorites

import android.content.Context
import androidx.room.Room
import com.techyourchance.architecture.common.database.MyRoomDatabase

class FavoriteQuestionsPresenter(
    val context: Context
) {
    private val myRoomDatabase by lazy {
        Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            "MyDatabase"
        ).build()
    }

    private val favoriteQuestionDao by lazy {
        myRoomDatabase.favoriteQuestionDao
    }

    val favoriteQuestions by lazy {
        favoriteQuestionDao.observe()
    }
}