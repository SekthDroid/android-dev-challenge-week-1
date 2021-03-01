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
package com.example.androiddevchallenge.features.pets

import com.example.androiddevchallenge.Genre
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.features.pets.GetPetsUseCase.ItemFilterer
import com.example.androiddevchallenge.filterGenre
import com.example.androiddevchallenge.obtainPets
import java.util.Locale

class GetPetsUseCase {
    fun interface ItemFilterer<T> {
        fun filter(criteria: String, items: List<T>): List<T>
    }

    private val filters = listOf<ItemFilterer<Pet>>(
        ItemFilterer { criteria, items ->
            items.filter { criteria in it.specie.name.toLowerCase(Locale.getDefault()) }
        },
        ItemFilterer { criteria, items ->
            items.filter { criteria in it.breed }
        },
        ItemFilterer { criteria, items ->
            items.filterGenre(if (criteria == "male") Genre.Male else Genre.Female)
        }
    )

    operator fun invoke(criteria: String): List<Pet> {
        val pets = obtainPets()

        if (criteria.isEmpty()) {
            return pets
        }

        return filters.map { it.filter(criteria, pets) }
            .flatten()
            .toSet()
            .toList()
    }
}
