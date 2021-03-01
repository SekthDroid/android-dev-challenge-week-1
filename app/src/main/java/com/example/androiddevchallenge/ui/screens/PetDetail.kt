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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Healing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.features.pet.PetViewModel
import com.example.androiddevchallenge.ui.components.PetItemDescription
import com.example.androiddevchallenge.ui.components.toData
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PetDetailScreen(
    navController: NavController,
    petId: String,
    viewModel: PetViewModel = viewModel()
) {
    LaunchedEffect(petId) {
        viewModel.loadPet(petId = petId)
    }

    Surface(color = MaterialTheme.colors.background) {
        val petState = viewModel.pet.observeAsState()

        if (petState.value != null) {
            val pet = petState.value!!
            PetDetail(pet = pet) {
                navController.popBackStack()
            }

            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { navController.popBackStack() }
                    .padding(12.dp)
            )

            AdoptButton(pet = pet) {
                viewModel.adoptPet(petId)
            }
        }
    }
}

@Composable
fun AdoptButton(pet: Pet, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick
        ) {
            Icon(Icons.Filled.Healing, null)
            Spacer(Modifier.width(4.dp))
            Text(text = "Adopt me!, Im so cute")
        }
    }
}

@Composable
fun PetDetail(pet: Pet, onBackPress: () -> Unit) {
    val context = LocalContext.current

    Box(contentAlignment = Alignment.BottomCenter) {
        CoilImage(
            data = pet.image.toData(),
            contentDescription = null,
            fadeIn = true,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        PetItemDescription(pet = pet)
    }
}
