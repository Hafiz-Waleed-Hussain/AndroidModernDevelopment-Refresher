package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain

data class Restaurant(
    val id: Int, val title: String, val description: String, val isFavourite: Boolean = false
)
