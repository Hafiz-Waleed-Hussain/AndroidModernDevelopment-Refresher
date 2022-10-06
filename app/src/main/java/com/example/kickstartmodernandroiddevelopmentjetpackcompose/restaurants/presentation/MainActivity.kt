package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.details.RestaurantDetailsScreen
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list.RestaurantsScreens
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list.RestaurantsViewModel
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.ui.theme.RestaurantsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RestaurantApp()
                }
            }
        }
    }

    @Composable
    private fun RestaurantApp() {

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "restaurants") {
            composable(route = "restaurants") {
                val viewModel: RestaurantsViewModel = hiltViewModel()
                RestaurantsScreens(
                    state = viewModel.state.value,
                    onItemClick = { id -> navController.navigate("restaurants/$id") },
                    onFavouriteClick = { id, oldValue ->
                        viewModel.toggleFavourite(id, oldValue)
                    }
                )
            }
            composable(route = "restaurants/{restaurant_id}",
                arguments = listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                }), deepLinks = listOf(navDeepLink {
                    uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
                })
            ) {
                RestaurantDetailsScreen()
            }
        }
    }
}

