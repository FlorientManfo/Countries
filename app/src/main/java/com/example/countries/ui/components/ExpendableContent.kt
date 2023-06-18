package com.example.countries.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.countries.R
import com.example.countries.data.modle.country.Country
import com.example.countries.ui.theme.CountriesTheme

@Composable
fun ExpendableContent(
    country: Country,
    modifier: Modifier = Modifier
){
//     Declaring a list of fields that i want to show on my screen
    val fields = mapOf(
        R.string.name to stringResource(R.string.name_format,country.name.official, country.cca3),
        R.string.identifier to stringResource(id = R.string.identifier_format,
            country.idd?.root?:"", country.idd?.suffixes?.firstOrNull()?:stringResource(id = R.string.undefined_value)),
        R.string.capital to country.capital?.firstOrNull(),
        R.string.population to stringResource(R.string.population_format, country.population),
        R.string.area to stringResource(R.string.area_format, country.area)
    )

    Column{
        fields.forEach {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_space)))
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_space)),
                verticalAlignment = Alignment.Top,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(it.key),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = it.value ?: stringResource(id = R.string.undefined_value),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun MorePreview(
){
    CountriesTheme {
//        More(country = country)
    }
}
