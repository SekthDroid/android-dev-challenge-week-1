package com.example.androiddevchallenge.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.Genre
import com.example.androiddevchallenge.ImageType
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.black20
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PetName(pet: Pet) {
    val genre = pet.toGenreView()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(style = MaterialTheme.typography.h6, color = Color.White, text = pet.name)
        Icon(
            painter = painterResource(id = genre.first),
            contentDescription = genre.second,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

private fun Pet.toGenreView(): Pair<Int, String> {
    return when (genre) {
        Genre.Female -> Pair(R.drawable.ic_female, "Female")
        else -> Pair(R.drawable.ic_male, "Male")
    }
}

@Composable
fun PetItemDescription(pet: Pet) {
    Box(
        modifier = Modifier
            .height(height = 100.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = black20)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            PetName(pet)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                text = "${pet.breed} ${pet.specie.name}"
            )
            Text(
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                text = "Age: ${pet.age}yo"
            )
        }
    }
}

@Composable
fun PetListItem(pet: Pet, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        CoilImage(
            data = pet.image.toData(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            fadeIn = true,
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            error = { error ->
                Log.w("PetImage", error.throwable)
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        PetItemDescription(pet = pet)
    }
}

private fun ImageType?.toData(): Any {
    return when (this) {
        is ImageType.ResourceImage -> drawableId
        is ImageType.NetworkImage -> url
        else -> 0
    }
}

