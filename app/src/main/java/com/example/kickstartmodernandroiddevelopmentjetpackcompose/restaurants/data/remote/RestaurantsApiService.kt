package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.remote

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.RemoteRestaurant
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApiService {

    @GET("restaurants.json")
    suspend fun getRestaurants(): List<RemoteRestaurant>

    @GET("restaurants.json?orderBy=\"r_id\" ")
    suspend fun getRestaurants(@Query("equalTo") id: Int): Map<String, RemoteRestaurant>
}