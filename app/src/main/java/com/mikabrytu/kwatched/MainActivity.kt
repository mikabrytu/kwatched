package com.mikabrytu.kwatched

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import coil3.compose.AsyncImage
import com.mikabrytu.kwatched.ui.theme.KwatchedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "kwatched-database")
            .allowMainThreadQueries() // TODO: Change this to async usage
            .build()

        enableEdgeToEdge()
        setContent {
            KwatchedTheme {
                Main(db)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(database: AppDatabase) {
    val movieDao = database.movieDao()
    val movies = movieDao.getAll()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Kwatched")
                }
            )
        },
        floatingActionButton = {
            NewMovieButton(movieDao)
        }
    ) { innerPadding ->
        Box(
            Modifier.padding(innerPadding)
        ) {
            MovieList(movies)
        }
    }
}

@Composable
fun NewMovieButton(movieDao: MovieDao) {
    val movies: List<Movie> = mutableListOf(
        Movie(
            id = 0,
            title = "The Lord of the Rings: The Fellowship of the Ring",
            posterUrl = "https://img2.wikia.nocookie.net/__cb20150203040819/lotr/images/7/74/LOTRFOTRmovie.jpg",
        ),
        Movie(
            id = 1,
            title = "The Lord of the Rings: The Two Towers",
            posterUrl = "https://1.bp.blogspot.com/-Q7LGLfCJMbg/TbKeTzen5_I/AAAAAAAAA7s/zyZWAzTCq4s/s1600/the-lord-of-the-rings-the-two-towers-poster-3.jpg",
        ),
        Movie(
            id = 2,
            title = "The Lord of the Rings: The Return of the King",
            posterUrl = "https://2.bp.blogspot.com/-djIuucN9UUM/TbKeWpumHrI/AAAAAAAAA70/ZlNcrYG3G6I/s1600/The-Lord-of-the-Rings-The-Return-of-the-King-movie-poster.jpg",
        ),
        Movie(
            id = 3,
            title = "The Hobbit: An Unexpected Journey",
            posterUrl = "https://image.tmdb.org/t/p/original/67DAaVjjFG7qyml1bu34PV17FKS.jpg",
        ),
        Movie(
            id = 4,
            title = "The Hobbit: The Desolation of Smaug",
            posterUrl = "https://image.tmdb.org/t/p/original/xQYiXsheRCDBA39DOrmaw1aSpbk.jpg",
        ),
        Movie(
            id = 5,
            title = "The Hobbit: The Battle of the Five Armies",
            posterUrl = "https://image.tmdb.org/t/p/original/qDza9vFPVfdJBQOcUSfKKT8Zd9i.jpg",
        ),
    )

    FloatingActionButton(
        shape = CircleShape,
        onClick = {
//            movies.forEach {
//                movieDao.insert(it)
//            }
            Log.println(Log.DEBUG, "NewMovieButton", "Add new movies to database")
        }
    ) {
        Icon(
            Icons.Filled.Add,
            "Add new movies to database"
        )
    }
}

@Composable
fun MovieList(movies: List<Movie>) {
    val gridSate = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridSate,
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 4.dp
        )
    ) {
        items(
            count = movies.size,
            key = { it }
        ) { index ->
            movies[index].posterUrl?.let { MovieCard(it) }
        }
    }
}

@Composable
fun MovieCard(url: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .height(196.dp)
            .padding(
                horizontal = 4.dp,
                vertical = 4.dp
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KwatchedTheme {
        MovieCard("https://img2.wikia.nocookie.net/__cb20150203040819/lotr/images/7/74/LOTRFOTRmovie.jpg")
    }
}