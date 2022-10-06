package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.RestaurantsApplication
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDao
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDb
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.remote.RestaurantsApiService
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.Restaurant
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private var restInterface: RestaurantsApiService,
    private var restaurantsDao: RestaurantsDao
) : ViewModel() {

    val state = mutableStateOf<Restaurant?>(null)

    init {
        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch {
            state.value = try {
                getRemoteRestaurant(id)
            } catch (e: Exception) {
                restaurantsDao.getAll().find { it.id == id }?.let {
                    Restaurant(id = it.id, title = it.title, description = it.description)
                }
            }
        }
    }

    private suspend fun getRemoteRestaurant(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            restInterface.getRestaurants(id).values.first().let {
                Restaurant(id = it.id, title = it.title, description = it.description)
            }
        }
    }
}