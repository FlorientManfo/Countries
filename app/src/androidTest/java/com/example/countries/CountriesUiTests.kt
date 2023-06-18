package com.example.countries

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countries.ui.screens.CountriesHomePage
import com.example.countries.ui.screens.CountriesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class `CountriesUiTests` {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Before
    val activity = composeTestRule.activity

    /**
     * This test asserts that back button is not visible one home screen
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun backButton_CountriesListScreen_isNotVisible(){
        val activity = composeTestRule.activity
        composeTestRule.setContent {
            val screenWidth = calculateWindowSizeClass(activity = activity).widthSizeClass
            val viewModel: CountriesViewModel = viewModel(
                factory = CountriesViewModel.Factory
            )
            CountriesHomePage(
                viewModel = viewModel,
                windowSize = screenWidth
            )
        }
        composeTestRule.onNodeWithTag(activity.getString(R.string.list_tag))
            .assertDoesNotExist()
    }
}