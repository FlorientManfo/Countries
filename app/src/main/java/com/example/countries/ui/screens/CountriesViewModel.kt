package com.example.countries.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.countries.CountriesApp
import com.example.countries.data.modle.country.Country
import com.example.countries.data.repository.CountriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

 /*This will hold the logic that will be
 use to display data from API to the views*/
class CountriesViewModel constructor(
    private val repository: CountriesRepository
): ViewModel() {

    init {
        getCountries()
    }

    private val _state = MutableStateFlow(CountriesState(
        null,
        listOf(),
        listOf()
    ))
    var state = _state.asStateFlow()
        private set

    var uiState: CountriesUiState by mutableStateOf(
        CountriesUiState.Loading
    )
        private set

    private fun getCountries(){
        viewModelScope.launch {
            uiState = try {
                val countries = repository.getCountries()
                _state.update {
                    it.copy(
                        currentCountry= countries.first(),
                        countries = countries,
                        allCountries = countries
                    )
                }
                CountriesUiState.Success
            }catch (e: IOException){
                CountriesUiState.Error(e.message ?: "An error occurred !")
            }
        }
    }

    /*Find function filtering countries list depending on the keyword and certain fields like
    official name,  region and sorting the result on official name alphabetically*/
    private fun findCountry(keyword: String){
       if(keyword.isNotEmpty()){
           _state.update {prevState ->
               prevState.copy(
                   countries = prevState.allCountries.filter {
                       it.name.official.lowercase().contains(keyword.lowercase())
                               || it.region.lowercase().contains(keyword.lowercase())
                   }.sortedBy { it.name.official }
               )
           }
       }else {
           getCountries()
       }
    }

    //Handle the UI events
    fun onEvent(event: CountriesUiEvent){
        when(event){
            is CountriesUiEvent.OnCountryClicked -> {
                _state.update {
                    it.copy(
                        currentCountry = event.country,
                        detailScreen = true
                    )
                }
            }
            is CountriesUiEvent.OnBackIconClicked -> {
                _state.update {
                    it.copy(
                        detailScreen = event.back
                    )
                }
            }
            is CountriesUiEvent.OnSubmitSearch ->{
                findCountry(event.keyWord)
            }
            is CountriesUiEvent.OnActiveChange ->{
                val currentCountry = _state.value.currentCountry
                getCountries()
                _state.update {
                    it.copy(
                        currentCountry = currentCountry,
                        activeSearch = event.active
                    )
                }
            }
        }
    }

    /*This companion object provide us the possibility to create
     an instance of the current viewModel with a parameters*/
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as CountriesApp
                val repository = application.container.countriesRepository
                CountriesViewModel(
                    repository = repository,
                )
            }
        }
    }
}