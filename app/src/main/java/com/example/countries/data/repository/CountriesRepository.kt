package com.example.countries.data.repository

import com.example.countries.data.modle.country.Country

//Here we have declare the functionalities of our repository
interface CountriesRepository {
    suspend fun getCountries(): List<Country>
}