package com.example.countries.data

import com.example.countries.data.network.CountriesApi
import com.example.countries.data.repository.CountriesRepository
import com.example.countries.data.repository.CountriesRepositoryImplementation
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val countriesRepository: CountriesRepository
}

class AppContainerImplementation: AppContainer {
    private val BASE_URL = "https://restcountries.com/v3.1/"

    //Building a retrofit instance
    private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

    //Create an API with retrofit instance
    private val retrofitService: CountriesApi by lazy {
        retrofit.create(CountriesApi::class.java)
    }

    override val countriesRepository: CountriesRepository by lazy{
        CountriesRepositoryImplementation(retrofitService)
    }
}