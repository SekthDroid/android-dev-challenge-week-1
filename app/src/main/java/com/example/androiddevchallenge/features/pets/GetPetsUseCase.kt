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