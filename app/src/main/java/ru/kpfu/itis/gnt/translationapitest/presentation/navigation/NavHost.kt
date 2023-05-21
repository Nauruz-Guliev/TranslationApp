package ru.kpfu.itis.gnt.translationapitest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.details.DetailsScreen
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.main.MainScreen

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Destinations.MAIN_SCREEN
    ) {
        composable(Destinations.MAIN_SCREEN) { MainScreen(navController = navController) }
        composable(
            Destinations.DETAILS + "{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
                id = backStackEntry.arguments?.getString("id")
            )
        }
    }
}

