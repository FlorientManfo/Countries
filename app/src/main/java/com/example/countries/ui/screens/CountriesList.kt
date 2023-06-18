package com.example.countries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.countries.R
import com.example.countries.data.modle.country.Country
import com.example.countries.ui.components.CountryRow

@Composable
fun CountriesList(
    showExpendableButton: Boolean,
    countries: List<Country>,
    onCardClicked: (country: Country) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_space)),
        modifier = modifier
    ){
        items(countries){country ->
            CountryRow(
                showExpendableButton = showExpendableButton,
                country = country,
                onCardClicked = {onCardClicked(country)}
            )
        }
    }
}