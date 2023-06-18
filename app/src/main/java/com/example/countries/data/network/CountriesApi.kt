package com.example.countries.data.network

import com.example.countries.data.modle.country.Country
import retrofit2.http.GET

interface CountriesApi{
    @GET("all")
    suspend fun getCountries() : List<Country>
}
