package com.example.countries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countries.R
import com.example.countries.data.modle.country.Country
import com.example.countries.ui.components.ExpendableContent
import com.example.countries.ui.components.MapView
import com.example.countries.ui.theme.CountriesTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun CountryDetails(
    country: Country,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current
  LazyColumn(
      modifier = Modifier
          .fillMaxSize(),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
      item {
          Card(
              elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_space)),
              colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
              modifier = modifier
                  .fillMaxWidth()
                  .height((configuration.screenHeightDp / 2).dp)
          ) {
              MapView(
                  latLng = LatLng(
                      country.latlng[0].toDouble(),
                      country.latlng[1].toDouble()
                  ),
                  title = country.name.official,
                  desc = ""
              )
          }
      }
        item { 
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_space)))
        }
      item {
          Card(
              elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_space)),
              colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
          ) {
              Row(
                  modifier = modifier
                      .wrapContentSize()
                      .padding(dimensionResource(id = R.dimen.medium_space))
              ) {
                  ExpendableContent(country = country)
              }

          }
      }
  }
}

@Composable
@Preview(showBackground = true)
fun CountryDetailsPreview(){
    CountriesTheme {
//        CountryDetails()
    }
}