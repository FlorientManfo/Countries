package com.example.countries.ui.screens

import com.example.countries.data.modle.country.Country

data  class CountriesState(
    val currentCountry: Country? = null,
    val countries: List<Country>,
    val allCountries: List<Country>,
    val detailScreen: Boolean = false,
    val activeSearch: Boolean = false
)