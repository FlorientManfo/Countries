package com.example.countries.ui.screens

//This will hold the states of the UI during countries loading
sealed interface CountriesUiState {
    object Success : CountriesUiState
    object Loading : CountriesUiState
    data class Error(val message: String) : CountriesUiState
}