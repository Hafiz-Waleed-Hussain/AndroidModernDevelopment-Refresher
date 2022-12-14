package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.domain.Restaurant
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.presentation.Description

@Composable
fun RestaurantsScreens(
    state: RestaurantsScreenState,
    onItemClick: (id: Int) -> Unit,
    onFavouriteClick: (id: Int, oldValue: Boolean) -> Unit
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)) {
            items(state.restaurants) { restaurant ->
                RestaurantItem(item = restaurant, onFavouriteClick = { id, oldValue ->
                    onFavouriteClick(id, oldValue)
                }, onItemClick = { id -> onItemClick(id) })
            }
        }
        if (state.isLoading)
            CircularProgressIndicator(
                Modifier.semantics {
                    this.contentDescription = Description.RESTAURANTS_LOADING
                }
            )
        if (state.error != null)
            Text(text = state.error)
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavouriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    val icon = if (item.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder

    Card(elevation = 4.dp, modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(item.id) }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(.15f))
            RestaurantDetails(
                item.title, item.description, Modifier.weight(0.70f)
            )
            RestaurantIcon(icon, Modifier.weight(.15f)) {
                onFavouriteClick(item.id, item.isFavourite)
            }
        }
    }
}

@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(text = title, style = MaterialTheme.typography.h6)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = description, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}
