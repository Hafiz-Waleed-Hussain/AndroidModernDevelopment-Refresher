package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
