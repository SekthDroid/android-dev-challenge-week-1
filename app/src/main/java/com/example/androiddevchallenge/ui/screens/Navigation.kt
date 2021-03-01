/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            PetDetailScreen(navController = navController, it.arguments?.getString("petId")!!)
        }
    }
}
