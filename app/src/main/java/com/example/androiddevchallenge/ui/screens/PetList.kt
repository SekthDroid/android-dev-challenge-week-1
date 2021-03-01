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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.features.pets.PetsViewModel
import com.example.androiddevchallenge.ui.components.PetListItem
import com.example.androiddevchallenge.ui.components.SearchField

@Composable
fun PetListScreen(
    navController: NavController,
    petViewModel: PetsViewModel = viewModel()
) {
    val pets: List<Pet> by petViewModel.pets.observeAsState(initial = emptyList())

    Surface(color = MaterialTheme.colors.background) {
        Box(Modifier) {
            Column {
                SearchField(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    petViewModel.filter(it)
                }
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(pets) { pet ->
                        PetListItem(pet = pet) {
                            navController.navigate("$ROUTE_PET_DETAIL/${pet.id}") {
                                popUpTo(ROUTE_PET_LIST) {}
                            }
                        }
                    }
                }
            }
        }
    }
}
