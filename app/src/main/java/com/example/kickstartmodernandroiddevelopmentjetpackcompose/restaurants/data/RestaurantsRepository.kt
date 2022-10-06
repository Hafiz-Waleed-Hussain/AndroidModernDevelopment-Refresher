package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data

import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.Restaurant
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.RestaurantsApplication
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.LocalRestaurant
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.PartialLocalRestaurant
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDao
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDb
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.remote.RestaurantsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private var restInterface: RestaurantsApiService,
    private var restaurantsDao: RestaurantsDao
) {
    internal suspend fun loadRestaurants() {
        withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty()) {
                            throw Exception("Something went wrong. We have no data. ")
                        }
                    }
                    else -> throw  e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favouriteRestaurants = restaurantsDao.getAllFavouried()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(it.id, it.title, it.description, false)
        })
        restaurantsDao.updateAll(favouriteRestaurants.map {
            PartialLocalRestaurant(it.id, true)
        })
    }

    internal suspend fun toggleFavouriteRestaurant(id: Int, value: Boolean) =
        withContext(Dispatchers.IO) {
            restaurantsDao.update(
                PartialLocalRestaurant(
                    id = id, isFavourite = value
                )
            )
            restaurantsDao.getAll()
        }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id, it.title, it.description, it.isFavorite)
            }
        }
    }

}