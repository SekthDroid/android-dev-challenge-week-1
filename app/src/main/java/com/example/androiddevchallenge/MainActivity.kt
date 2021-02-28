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

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.black20
import dev.chrisbanes.accompanist.coil.CoilImage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "puppyList") {
        composable("puppyList") {
            PuppyList(navController = navController, pets = obtainPets())
        }
        composable("puppyDetail") {
        }
    }
    Surface(color = MaterialTheme.colors.background) {
        PuppyList(navController = navController, pets = obtainPets())
    }
}

@Composable
fun SearchField(modifier: Modifier) {
    val (text, setText) = remember {
        mutableStateOf("")
    }
    Card(elevation = 8.dp, modifier = modifier, shape = CircleShape) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = text,
                onValueChange = { value -> setText(value) },
                textStyle = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
            )
        }
    }
}

@Composable
fun PuppyList(navController: NavController, pets: List<Pet>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier) {
        Column {
            SearchField(
                modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn(Modifier.fillMaxWidth()) {
                items(pets) { puppy ->
                    PuppyItem(puppy = puppy) {
                        Toast.makeText(
                            context,
                            "Clicked on ${puppy.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}

private fun ImageType?.toData(): Any {
    return when (this) {
        is ImageType.ResourceImage -> drawableId
        is ImageType.NetworkImage -> url
        else -> 0
    }
}

@Composable
fun PuppyItem(puppy: Pet, onClick: () -> Unit) {
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
            data = puppy.image.toData(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

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
                PetName(puppy)
                Text(
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    text = "${puppy.species.name} ${puppy.breed}"
                )
                Text(
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    text = "Age: ${puppy.age}yo"
                )
            }
        }
    }
}

private fun Pet.toGenreView(): Pair<Int, String> {
    return when (genre) {
        Genre.Female -> Pair(R.drawable.ic_female, "Female")
        else -> Pair(R.drawable.ic_male, "Male")
    }
}

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

@Composable
fun PuppyDetail(navController: NavController) {
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
