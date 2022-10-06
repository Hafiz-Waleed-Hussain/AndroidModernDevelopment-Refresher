package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDao
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.local.RestaurantsDb
import com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants.data.remote.RestaurantsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantsModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): RestaurantsDb {
        return Room.databaseBuilder(
            applicationContext,
            RestaurantsDb::class.java, "restaurants_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRoomDao(database: RestaurantsDb): RestaurantsDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("firebase url").build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RestaurantsApiService {
        return retrofit.create(RestaurantsApiService::class.java)
    }
}