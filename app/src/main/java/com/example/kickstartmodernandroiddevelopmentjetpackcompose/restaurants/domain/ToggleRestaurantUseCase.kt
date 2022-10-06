package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.RestaurantsRepository
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.GetSortedRestaurantsUseCase
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.Restaurant
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val newFav = oldValue.not()
        repository.toggleFavouriteRestaurant(id, newFav)
        return getSortedRestaurantsUseCase()
    }
}