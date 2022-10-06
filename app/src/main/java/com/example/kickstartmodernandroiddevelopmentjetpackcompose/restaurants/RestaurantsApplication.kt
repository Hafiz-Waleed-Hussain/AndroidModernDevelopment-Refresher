package com.example.kickstartmodernandroiddevelopmentjetpackcompose.restaurants

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RestaurantsApplication : Application() {

    init {
        app = this
    }

    companion object {
        lateinit var app: RestaurantsApplication
        fun getAppContext(): Context = app.applicationContext
    }
}