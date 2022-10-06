package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalRestaurant::class], version = 2, exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantsDao
}