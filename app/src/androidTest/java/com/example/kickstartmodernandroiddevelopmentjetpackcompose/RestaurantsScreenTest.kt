package com.example.kickstartmodernandroiddevelopmentjetpackcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.Description
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list.RestaurantsScreenState
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list.RestaurantsScreens
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.ui.theme.RestaurantsAppTheme
import org.junit.Rule
import org.junit.Test

class RestaurantsScreenTest {

    @get: Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreens(state = RestaurantsScreenState(
                    restaurants = emptyList(), isLoading = true
                ), onItemClick = {}, onFavouriteClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onNodeWithContentDescription(Description.RESTAURANTS_LOADING).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {
        val restaurants = DummyContent.getDomainRestaurants()
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreens(state = RestaurantsScreenState(
                    restaurants = restaurants, isLoading = false
                ), onItemClick = {}, onFavouriteClick = { _, _ -> })

            }
        }
        testRule.onNodeWithText(restaurants[0].title).assertIsDisplayed()
        testRule.onNodeWithText(restaurants[0].description).assertIsDisplayed()
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun stateWith_ClickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreens(state = RestaurantsScreenState(
                    restaurants = restaurants, isLoading = false
                ), onItemClick = { id ->
                    assert(id == targetRestaurant.id)
                }, onFavouriteClick = { _, _ -> })
            }
        }

        testRule.onNodeWithText(targetRestaurant.title).performClick()
    }
}