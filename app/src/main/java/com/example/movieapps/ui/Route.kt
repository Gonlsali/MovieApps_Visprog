package com.example.movieapps.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapps.ui.view.ListMovieView
import com.example.movieapps.ui.view.MovieDetailView
import com.example.movieapps.ui.view.ProfileView
import com.example.movieapps.viewmodel.ListMovieUIState
import com.example.movieapps.viewmodel.ListMovieViewModel
import com.example.movieapps.viewmodel.MovieDetailUiState
import com.example.movieapps.viewmodel.MovieDetailViewModel

enum class ListScreen() {
    ListMovieView,
    MovieDetailView,
    ProfileView
}

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppsRoute() {

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(

    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListScreen.ListMovieView.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(ListScreen.ListMovieView.name) {
                val listMovieViewModel: ListMovieViewModel = viewModel()
                val status = listMovieViewModel.listMovieUIState

                when(status) {
                    is ListMovieUIState.Loading -> {}
                    is ListMovieUIState.Success -> ListMovieView(
                        movieList = status.data,
                        onFavClicked = {
                            listMovieViewModel.onFavClicked(it)
                        }
                    )
                    is ListMovieUIState.Error -> {}
                }
            }
            composable(ListScreen.MovieDetailView.name+"{movieId}") {
                val movieDetailViewModel: MovieDetailViewModel = viewModel()
                movieDetailViewModel.getMovieById(it.arguments?.getString("movieId")!!.toInt())

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
            composable(ListScreen.ProfileView.name) {
                ProfileView()
            }
        }
    }
}
