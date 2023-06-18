package com.example.countries.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.countries.R

@Composable
fun LoadingIndicator(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
){
    val animatedColor = animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(
                dimensionResource(id =
                if (windowSize == WindowWidthSizeClass.Compact) R.dimen.medium_size
                else R.dimen.large_size
                )
            ),
            color = animatedColor.value
        )
    }
}

@Preview
@Composable
fun LoadingIndicatorPreview(){
    LoadingIndicator(
        WindowWidthSizeClass.Compact
    )
}
