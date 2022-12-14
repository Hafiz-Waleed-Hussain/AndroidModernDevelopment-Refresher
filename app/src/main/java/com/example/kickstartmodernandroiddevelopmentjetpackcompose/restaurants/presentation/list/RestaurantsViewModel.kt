package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.GetInitialRestaurantsUseCase
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val getInitialRestaurantsUseCase: GetInitialRestaurantsUseCase,
    private val toggleRestaurantUseCase: ToggleRestaurantUseCase
) : ViewModel() {

    private val _state = mutableStateOf(
        RestaurantsScreenState(
            restaurants = listOf(), isLoading = true
        )
    )
    val state: State<RestaurantsScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message, isLoading = false
        )
    }

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            val restaurants = getInitialRestaurantsUseCase()
            _state.value = _state.value.copy(restaurants = restaurants, isLoading = false)
        }
    }

    fun toggleFavourite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val toggleFavouriteRestaurant = toggleRestaurantUseCase(id, oldValue)
            _state.value = _state.value.copy(restaurants = toggleFavouriteRestaurant)
        }
    }

}
