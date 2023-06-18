package com.example.countries

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countries.ui.screens.CountriesHomePage
import com.example.countries.ui.screens.CountriesViewModel
import com.example.countries.ui.theme.CountriesTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: CountriesViewModel = viewModel(
                factory = CountriesViewModel.Factory
            )
            CountriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val widthSizeClass = calculateWindowSizeClass(activity = this)

                    CountriesHomePage(
                        viewModel = viewModel,
                        windowSize = widthSizeClass.widthSizeClass,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.medium_space))
                    )
                }
            }
        }
    }
}

