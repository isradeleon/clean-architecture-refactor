package com.techyourchance.architecture.ui.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techyourchance.architecture.common.database.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.ui.favorites.FavoriteQuestionsPresenter
import com.techyourchance.architecture.ui.favorites.FavoriteQuestionsScreen
import com.techyourchance.architecture.ui.navigation.Route
import com.techyourchance.architecture.ui.navigation.ScreensNavigator
import com.techyourchance.architecture.ui.question_details.QuestionDetailsScreen
import com.techyourchance.architecture.ui.questions_list.QuestionsListScreen

@Composable
fun MainScreen(
    stackoverflowApi: StackoverflowApi,
    favoriteQuestionDao: FavoriteQuestionDao,
) {
    val screensNavigator = remember { ScreensNavigator() }

    val currentRoute = screensNavigator.currentRoute.collectAsState()
    val currentBottomTab = screensNavigator.currentBottomTab.collectAsState()
    val isRootRoute = screensNavigator.isRootRoute.collectAsState()
    val isShowFavoriteButton = currentRoute.value?.routeName == Route.QuestionDetailsScreen().routeName

    val questionIdAndTitle = remember(currentRoute.value) {
        if (currentRoute.value is Route.QuestionDetailsScreen) {
            val details = currentRoute.value as Route.QuestionDetailsScreen
            Pair(details.questionId, details.questionTitle)
        } else {
            Pair("", "")
        }
    }

    var isFavoriteQuestion by remember { mutableStateOf(false) }
    if (isShowFavoriteButton && questionIdAndTitle.first.isNotEmpty()) {
        LaunchedEffect(questionIdAndTitle) {
            favoriteQuestionDao.observeById(questionIdAndTitle.first).collect { favoriteQuestion ->
                isFavoriteQuestion = favoriteQuestion != null
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                isRootRoute = isRootRoute.value,
                showFavoriteButton = isShowFavoriteButton,
                favoriteQuestionDao = favoriteQuestionDao,
                questionIdAndTitle = questionIdAndTitle,
                isFavoriteQuestion = isFavoriteQuestion,
                onBackClick = { screensNavigator.navigateBack() }
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                MyBottomTabsBar(
                    bottomTabs = ScreensNavigator.BOTTOM_TABS,
                    currentBottomTab = currentBottomTab.value,
                    onTabClicked = { bottomTab -> screensNavigator.toTab(bottomTab) }
                )
            }
        },
        content = { padding ->
            MainScreenContent(
                padding = padding,
                stackoverflowApi = stackoverflowApi,
                favoriteQuestionDao = favoriteQuestionDao,
                screensNavigator = screensNavigator
            )
        }
    )
}

@Composable
private fun MainScreenContent(
    padding: PaddingValues,
    screensNavigator: ScreensNavigator,
    stackoverflowApi: StackoverflowApi,
    favoriteQuestionDao: FavoriteQuestionDao,
) {
    val parentNavController = rememberNavController()
    screensNavigator.setParentNavController(parentNavController)

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp),
    ) {
        val favoriteQuestionsPresenter = remember {
            FavoriteQuestionsPresenter(favoriteQuestionDao)
        }

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = parentNavController,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
            startDestination = Route.MainTab.routeName,
        ) {
            composable(route = Route.MainTab.routeName) {
                val mainNestedNavController = rememberNavController()
                screensNavigator.setNestedNavController(mainNestedNavController)

                NavHost(navController = mainNestedNavController, startDestination = Route.QuestionsListScreen.routeName) {
                    composable(route = Route.QuestionsListScreen.routeName) {
                        QuestionsListScreen(
                            onQuestionClicked = { clickedQuestionId, clickedQuestionTitle ->
                                screensNavigator.toRoute(
                                    Route.QuestionDetailsScreen(
                                        clickedQuestionId, clickedQuestionTitle
                                    )
                                )
                            },
                        )
                    }
                    composable(route = Route.QuestionDetailsScreen().routeName) { backStackEntry ->
                        QuestionDetailsScreen(
                            questionId = backStackEntry.arguments?.getString("questionId")!!,
                            stackoverflowApi = stackoverflowApi,
                            favoriteQuestionDao = favoriteQuestionDao,
                            onError = { screensNavigator.navigateBack() }
                        )
                    }
                }

            }

            composable(route = Route.FavoritesTab.routeName) {
                val favoritesNestedNavController = rememberNavController()
                screensNavigator.setNestedNavController(favoritesNestedNavController)

                NavHost(navController = favoritesNestedNavController, startDestination = Route.FavoriteQuestionsScreen.routeName) {
                    composable(route = Route.FavoriteQuestionsScreen.routeName) {
                        FavoriteQuestionsScreen(
                            favoriteQuestionsPresenter = favoriteQuestionsPresenter,
                            onQuestionClicked = { favoriteQuestionId, favoriteQuestionTitle ->
                                screensNavigator.toRoute(
                                    Route.QuestionDetailsScreen(
                                        favoriteQuestionId, favoriteQuestionTitle
                                    )
                                )
                            }
                        )
                    }
                    composable(route = Route.QuestionDetailsScreen().routeName) { backStackEntry ->
                        QuestionDetailsScreen(
                            questionId = backStackEntry.arguments?.getString("questionId")!!,
                            stackoverflowApi = stackoverflowApi,
                            favoriteQuestionDao = favoriteQuestionDao,
                            onError = { screensNavigator.navigateBack() }
                        )
                    }
                }
            }
        }
    }
}