package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.LocalRestaurant
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.PartialLocalRestaurant

@Dao
interface RestaurantsDao {
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalRestaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurant: List<LocalRestaurant>)

    @Update(entity = LocalRestaurant::class)
    suspend fun update(partialRestaurant: PartialLocalRestaurant)

    @Update(entity = LocalRestaurant::class)
    suspend fun updateAll(partialRestaurants: List<PartialLocalRestaurant>)

    @Query("SELECT * FROM restaurants WHERE is_favourite = 1")
    suspend fun getAllFavouried(): List<LocalRestaurant>
}