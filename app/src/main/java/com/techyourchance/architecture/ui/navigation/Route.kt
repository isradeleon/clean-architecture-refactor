package com.techyourchance.architecture.ui.navigation

sealed class Route(val routeName: String) {
    data object MainTab: Route("mainTab")
    data object FavoritesTab: Route("favoritesTab")
    data object QuestionsListScreen: Route("questionsList")
    data object QuestionDetailsScreen: Route("questionDetails/{questionId}/{questionTitle}")
    data object FavoriteQuestionsScreen: Route("favorites")
}