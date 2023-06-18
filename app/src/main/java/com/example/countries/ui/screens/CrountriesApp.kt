package com.example.countries.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.countries.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.countries.ui.components.ErrorCard
import com.example.countries.ui.components.LoadingIndicator
import com.example.countries.ui.theme.CountriesTheme
import com.example.countries.utils.hideSoftKeyboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesTopAppBar(
    @StringRes title: Int,
    showBackIcon: Boolean,
    onBackClicked: (back: Boolean) -> Unit,
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        navigationIcon = {
            AnimatedVisibility(visible = showBackIcon) {
                IconButton(
                    modifier = Modifier.testTag(stringResource(id = R.string.back)),
                    onClick = {
                    onBackClicked(false)
                } ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesHomePage(
    windowSize: WindowWidthSizeClass,
    viewModel: CountriesViewModel,
    modifier: Modifier = Modifier,
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    BackHandler() {
       if(state.value.detailScreen){
           viewModel.onEvent(CountriesUiEvent.OnBackIconClicked(false))
       }else {
           (context as Activity).finish()
       }
    }

    Scaffold(
        topBar =  {
            CountriesTopAppBar(
                showBackIcon = state.value.detailScreen,
                title = if (!state.value.detailScreen) R.string.app_name
                else R.string.details_page_title,
            ){
                viewModel.onEvent(CountriesUiEvent.OnBackIconClicked(it))
            }
        },
    ) {padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_space))
        ) {
            AnimatedVisibility(
                visible = !state.value.detailScreen,
                enter = slideIn(
                    animationSpec = tween()
                ) {
                    IntOffset(0, 100)
                } + fadeIn(
                    animationSpec = tween()
                ),
                exit = slideOut(
                    animationSpec = tween()
                ) {
                    IntOffset(0, -100)
                } + fadeOut(
                    animationSpec = tween()
                )
            ) {
                SearchBar(
                    leadingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(CountriesUiEvent.OnSubmitSearch(searchQuery))
                            hideSoftKeyboard(context as Activity)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    query = searchQuery,
                    onQueryChange = {text ->
                        searchQuery = text
                        viewModel.onEvent(CountriesUiEvent.OnSubmitSearch(text))
                    },
                    onSearch = {query ->
                        viewModel.onEvent(CountriesUiEvent.OnSubmitSearch(query))
                        hideSoftKeyboard(context as Activity)
                    },
                    active = false,
                    onActiveChange = {state -> viewModel.onEvent(CountriesUiEvent.OnActiveChange(state))}
                ) {}
            }
            ScreenLayout(
                windowSize = windowSize,
                viewModel = viewModel,
                modifier = modifier
            )
        }
    }
}


@Composable
fun ScreenLayout(
    windowSize: WindowWidthSizeClass,
    viewModel: CountriesViewModel,
    modifier: Modifier = Modifier
){
    val state = viewModel.state.collectAsState()
    when(viewModel.uiState){
        is CountriesUiState.Loading ->{
            LoadingIndicator(
                windowSize = windowSize,
                modifier = modifier
            )
        }
        is CountriesUiState.Success ->{
            when(windowSize){
                WindowWidthSizeClass.Compact -> {
                    if(!state.value.detailScreen){
                        CountriesList(
                            showExpendableButton = windowSize == WindowWidthSizeClass.Compact,
                            modifier = modifier,
                            countries = state.value.countries,
                            onCardClicked = {country ->
                                viewModel.onEvent(CountriesUiEvent.OnCountryClicked(country))
                                viewModel.onEvent(CountriesUiEvent.OnBackIconClicked(true))
                            },
                        )
                    } else {
                        CountryDetails(
                            country = state.value.currentCountry
                                ?:state.value.allCountries.first(),
                        )
                    }
                }
                else ->{
                    ExpendedAndMediumLayout(
                        modifier = modifier,
                        leftContent = {
                            CountriesList(
                                showExpendableButton = windowSize == WindowWidthSizeClass.Compact,
                                countries = state.value.countries,
                                onCardClicked = {
                                        country -> viewModel.onEvent(CountriesUiEvent.OnCountryClicked(country))
                                },
                            )
                        },
                        rightContent = {
                            CountryDetails(
                                country = state.value.currentCountry ?: state.value.allCountries.first(),
                                modifier = modifier,
                            )
                        }
                    )
                }
            }
        }
        is CountriesUiState.Error ->{
            val error = viewModel.uiState as CountriesUiState.Error
            ErrorCard(
                modifier = modifier,
                windowSize = windowSize,
                message = error.message,
            )
        }
    }
}

@Composable
fun ExpendedAndMediumLayout(
    leftContent: @Composable (
        modifier: Modifier
            ) -> Unit,
    rightContent: @Composable (
        modifier: Modifier
            ) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxSize()
            .testTag(stringResource(id = R.string.medium_expended_layout_tag)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_space)),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {

            leftContent(
                modifier = modifier
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            rightContent(
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview(){
    CountriesTheme() {
        CountriesTopAppBar(R.string.app_name, showBackIcon = false){}
    }
}









