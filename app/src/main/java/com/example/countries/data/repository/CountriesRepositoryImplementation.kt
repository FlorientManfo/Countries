package com.example.countries.data.repository

import com.example.countries.data.network.CountriesApi
import com.example.countries.data.modle.country.Country

//Here we now implement the functions of our repository
class CountriesRepositoryImplementation constructor(
    private val api: CountriesApi
): CountriesRepository {

    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}