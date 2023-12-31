package com.example.movieapps.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapps.data.DataSource
import com.example.movieapps.model.Movie
import com.example.movieapps.viewmodel.MovieDetailUiState
import com.example.movieapps.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailView(
    movie: Movie,
    onFavClicked: (Movie) -> Unit
) {

    var isLikedView by rememberSaveable { mutableStateOf(movie.isLiked) }

    Column() {
        Box(
            contentAlignment = Alignment.BottomEnd
        ){
            Image(
                painter = painterResource(id = movie.poster_path),
                contentDescription = "Image 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillWidth
            )

            FloatingActionButton(
                onClick = {
                    onFavClicked(movie)
                    isLikedView = movie.isLiked},
                shape = CircleShape,
                modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = if (isLikedView){
                        Color(0xFFEC407A)
                    } else {
                        Color(0xFF616161)
                    }
                )
            }
        }

        Row (
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = movie.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(2f)
                    .height(60.dp)
            )

            Text(
                text = "(${movie.getYear()})",
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

        }

        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color(0xFFFDCC0D)
            )
            Text(
                text = "${movie.vote_average}/10.0",
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Text(
            text = movie.overview,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Left
        )
    }
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
private fun MovieDetailPreview() {
    val movieDetailViewModel: MovieDetailViewModel = viewModel()
    movieDetailViewModel.getMovieById(4)

    val status = movieDetailViewModel.movieDetailUiState
    when(status){
        is MovieDetailUiState.Loading -> {}
        is MovieDetailUiState.Success -> {
            MovieDetailView(
                movie = status.data,
                onFavClicked = {}
            )
        }
        is MovieDetailUiState.Error -> {}
    }
}