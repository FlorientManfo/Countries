package com.example.countries.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.countries.R
import com.example.countries.data.modle.country.Country
import com.example.countries.ui.theme.CountriesTheme

/* Define the structure of the composable
 to hold teh country object in the list*/
@Composable
fun CountryRow(
    showExpendableButton: Boolean,
    country: Country,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val expended = remember {
        mutableStateOf(false)
    }
    val rotationAnimation by animateFloatAsState(
        targetValue = if(expended.value) 180f else 0f,
        animationSpec = tween(1000)
    )

    val animatedBackground by animateColorAsState(
        targetValue = if(expended.value)MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.onTertiary
    )

    Card(
        modifier = modifier
            .clickable { onCardClicked() }
            .testTag(country.name.official),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small_space)),
        colors = CardDefaults.cardColors(animatedBackground)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_space)),
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.medium_space))
                .animateContentSize(
                    animationSpec = tween()
                ) { initialValue, targetValue -> }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_space)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = country.flags.png),
                    contentDescription = stringResource(id = R.string.flag),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensionResource(id = R.dimen.medium_size))
                        .background(Color.Black),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = modifier.weight(2f)
                ) {

                    Text(
                        text = country.name.official,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .testTag(stringResource(id = R.string.name))
                    )
                    Text(
                        text = country.region,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .testTag(stringResource(id = R.string.region))
                    )
                }

                if(showExpendableButton){
                    IconButton(
                        onClick = { expended.value = !expended.value }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                            contentDescription = stringResource(id = R.string.expend),
                            modifier = Modifier.rotate(rotationAnimation),
                        )
                    }
                }
            }
            AnimatedVisibility(visible = expended.value) {
                Divider( thickness = 1.dp )
                ExpendableContent(
                    country = country,
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCountryPreview(){
    CountriesTheme() {
      /*  ItemCountry(
            onMoreClicked = { *//*TODO*//* }
        )*/
    }
}
