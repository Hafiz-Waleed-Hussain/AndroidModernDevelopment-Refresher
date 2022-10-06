package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(private val repository: RestaurantsRepository) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }
}