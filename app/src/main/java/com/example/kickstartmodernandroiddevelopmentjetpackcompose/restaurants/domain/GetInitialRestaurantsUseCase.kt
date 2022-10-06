package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }
}