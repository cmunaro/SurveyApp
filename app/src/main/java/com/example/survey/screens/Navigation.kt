package com.example.survey.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.survey.screens.main.MainPage
import com.example.survey.screens.survey.ui.SurveyPage
import com.example.survey.screens.survey.ui.SurveyPageRoute

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainPageRoute
    ) {
        composable<MainPageRoute> {
            MainPage(
                onGoToSurvey = { navController.navigate(SurveyPageRoute) }
            )
        }

        composable<SurveyPageRoute> {
            SurveyPage(
                onExit = { navController.popBackStack() }
            )
        }
    }
}