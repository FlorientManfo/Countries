package com.example.countries

import android.app.Application
import com.example.countries.data.AppContainer
import com.example.countries.data.AppContainerImplementation


class CountriesApp: Application(){

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImplementation()
    }
}