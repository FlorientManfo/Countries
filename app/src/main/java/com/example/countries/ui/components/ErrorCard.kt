package com.example.countries.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.countries.R
import com.example.countries.ui.theme.CountriesTheme

@Composable
fun ErrorCard(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    message: String? = null
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Card {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_space))
            ) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = stringResource(id = R.string.error_icon),
                    modifier = Modifier.size(
                        if(windowSize == WindowWidthSizeClass.Compact) dimensionResource(id = R.dimen.medium_size)
                        else dimensionResource(id = R.dimen.large_size)
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_space)))
                Text(
                    text = message ?: stringResource(id = R.string.network_error_message),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun ErrorCardPreview(){
    CountriesTheme {
        ErrorCard(
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}