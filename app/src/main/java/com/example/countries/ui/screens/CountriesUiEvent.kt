package com.example.countries.ui.screens

import com.example.countries.data.modle.country.Country

sealed interface CountriesUiEvent {
    data class OnCountryClicked(val country: Country): CountriesUiEvent
    data class OnBackIconClicked(val back: Boolean ): CountriesUiEvent
    data class OnSubmitSearch(val keyWord: String): CountriesUiEvent
    data class OnActiveChange(val active: Boolean): CountriesUiEvent
}