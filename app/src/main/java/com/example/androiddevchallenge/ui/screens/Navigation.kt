package com.example.androiddevchallenge.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument

const val ROUTE_PET_LIST = "petList"
const val ROUTE_PET_DETAIL = "petDetail"

@Composable
fun PetNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ROUTE_PET_LIST) {
        composable(ROUTE_PET_LIST) {
            PetListScreen(navController = navController)
        }
        composable(
            "$ROUTE_PET_DETAIL/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) {
            PetDetail(navController = navController, it.arguments?.getString("petId"))
        }
    }
}