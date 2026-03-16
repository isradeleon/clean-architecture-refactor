package com.techyourchance.architecture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.techyourchance.architecture.common.database.MyRoomDatabase
import com.techyourchance.architecture.ui.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var myRoomDatabase: MyRoomDatabase

    private val favoriteQuestionDao by lazy {
        myRoomDatabase.favoriteQuestionDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainScreen(
                    favoriteQuestionDao = favoriteQuestionDao
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}
