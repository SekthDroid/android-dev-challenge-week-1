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
package com.example.androiddevchallenge

data class Pet(
    val name: String,
    val species: Type,
    val breed: String,
    val age: Int,
    val genre: Genre,
    val description: String = "",
    val image: ImageType? = null
)

sealed class ImageType {
    data class ResourceImage(val drawableId: Int) : ImageType()
    data class NetworkImage(val url: String) : ImageType()
}

enum class Genre {
    Male,
    Female
}

enum class Type {
    Dog,
    GuineaPig,
    Rabbit,
    Hedgehog
}

fun obtainPets() = listOf(
    Pet(
        "Thor",
        species = Type.Rabbit,
        "Belier",
        1,
        Genre.Male,
        image = ImageType.ResourceImage(R.drawable.thor)
    ),
    Pet(
        "Odin",
        species = Type.Dog,
        "Pomerania",
        age = 1,
        genre = Genre.Male,
        image = ImageType.ResourceImage(R.drawable.odin)
    ),
    Pet(
        "Lúa",
        species = Type.Rabbit,
        "Belier",
        1,
        genre = Genre.Female,
        image = ImageType.ResourceImage(R.drawable.lua)
    ),
    Pet(
        "Dana",
        species = Type.Dog,
        "Cross",
        age = 1,
        genre = Genre.Female,
        image = ImageType.ResourceImage(R.drawable.dana)
    ),
    Pet(
        "Kuma",
        species = Type.Rabbit,
        "Belier",
        age = 2,
        genre = Genre.Female,
        image = ImageType.ResourceImage(R.drawable.kuma)
    ),
    Pet(
        "Java",
        species = Type.GuineaPig,
        "Peruvian",
        2,
        Genre.Female,
        image = ImageType.ResourceImage(
            drawableId = R.drawable.java
        )
    ),
    Pet(
        "Luna",
        species = Type.GuineaPig,
        "Abyssinian",
        2,
        Genre.Female,
        image = ImageType.ResourceImage(R.drawable.luna)
    ),
    Pet(
        "Nia",
        species = Type.GuineaPig,
        "Abyssinian",
        4,
        Genre.Female,
        image = ImageType.ResourceImage(R.drawable.nia)
    ),
    Pet(
        "Amy",
        species = Type.Hedgehog,
        "African",
        5,
        Genre.Female,
        image = ImageType.ResourceImage(R.drawable.amy)
    )
)
