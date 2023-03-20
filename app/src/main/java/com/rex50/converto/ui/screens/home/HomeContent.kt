package com.rex50.converto.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.rex50.converto.R
import com.rex50.converto.ui.components.*
import com.rex50.converto.ui.components.core.AnimatedVisibilityBox
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.ui.theme.ConvertoTheme
import com.rex50.converto.utils.Data

enum class CardType {
    FROM, TO
}

private object Keys {
    const val LOADER = "loader"
    const val ERROR = "error"
    const val TO_CARD = "To card"
    const val OTHER_CONVERSIONS = "Other conversions"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    viewModel: HomeContentViewModel = hiltViewModel()
) {

    val currencies by viewModel.currencies.collectAsState()
    val amountToBeConverted by viewModel.amountTobeConverted.collectAsState()
    val selectedFromCurrency by viewModel.selectedFromCurrency.collectAsState()
    val selectedToCurrency by viewModel.selectedToCurrency.collectAsState()

    var changingCurrencyForCard by remember { mutableStateOf(CardType.FROM) }
    var showCurrencyChanger by remember { mutableStateOf(false) }
    var showInfoSheet by remember { mutableStateOf(false) }

    ConstraintLayout {

        val (header, conversionRateCard, cardFromCurrency, listCurrenciesRefs) = createRefs()

        Header(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            showInfoSheet = true
        }

        // Conversion rate text
        AnimatedVisibilityBox(
            modifier = Modifier
                .animateContentSize()
                .constrainAs(conversionRateCard) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            isVisible = currencies is Data.Successful
        ) {
            SimpleTextBannerCard(
                text = viewModel.getFormattedConversionRate(
                    fromCurrency = selectedFromCurrency,
                    toCurrency = selectedToCurrency
                ),
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 16.dp
                    )
            )
        }

        // From card - for user to enter amount
        AnimatedVisibilityBox(
            modifier = Modifier
                .constrainAs(cardFromCurrency) {
                    top.linkTo(conversionRateCard.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            isVisible = currencies is Data.Successful
        ) {
            ConversionAmountCard(
                label = stringResource(R.string.from_currency),
                selectedCurrency = selectedFromCurrency,
                amount = amountToBeConverted,
                onChangeAmount = {
                    viewModel.convert(it)
                },
                onChangeCurrency = {
                    changingCurrencyForCard = CardType.FROM
                    showCurrencyChanger = true
                },
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    )
            )
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(listCurrenciesRefs) {
                    top.linkTo(cardFromCurrency.bottom, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currencies.let { result ->

                // Loader - show while fetching latest rates
                item(key = Keys.LOADER) {
                    if (result is Data.Loading) {
                        SimpleContentLoader(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(top = 64.dp)
                        )
                    }
                }

                // Error box with retry button
                item(key = Keys.ERROR) {
                    AnimatedVisibilityBox(isVisible = result is Data.Error) {
                        if (result is Data.Error) {
                            ErrorCard(
                                modifier = Modifier.padding(top = 64.dp),
                                message = result.message
                            ) {
                                viewModel.fetchCurrencies()
                            }
                        }
                    }
                }

                // To card - where user can see the conversion amount
                item(key = Keys.TO_CARD) {
                    AnimatedVisibilityBox(isVisible = result is Data.Successful) {
                        IconButton(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    MaterialTheme.shapes.large
                                )
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp
                                ),
                            onClick = { viewModel.swapCurrency() }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.round_swap_vert_24),
                                contentDescription = "Swap currency",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                    AnimatedVisibilityBox(isVisible = result is Data.Successful) {
                        ConversionAmountCard(
                            label = stringResource(R.string.to_currency),
                            selectedCurrency = selectedToCurrency,
                            amount = viewModel.getFormattedCurrencyAmount(
                                amount = selectedToCurrency.convertedCurrency,
                                currencyCode = selectedToCurrency.currency
                            ),
                            enabled = false,
                            onChangeAmount = {},
                            onChangeCurrency = {
                                changingCurrencyForCard = CardType.TO
                                showCurrencyChanger = true
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }


                if (result is Data.Successful) {
                    // Also show other conversions, if user has entered any amount
                    stickyHeader(key = Keys.OTHER_CONVERSIONS) {
                        AnimatedVisibilityBox(
                            isVisible = amountToBeConverted.isNotBlank(),
                            defaultTransitionDuration = 100
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.other_conversions,
                                    selectedFromCurrency.currency
                                ),
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(
                                        top = 24.dp,
                                        bottom = 16.dp
                                    )
                                    .animateItemPlacement()
                            )
                        }
                    }

                    if (amountToBeConverted.isNotBlank()) {
                        items(
                            count = result.data.size,
                            key = { index -> result.data[index].currency }
                        ) { index ->
                            result.data[index].apply {
                                CurrencyListItem(
                                    currency = this,
                                    formattedAmount = viewModel.getFormattedCurrencyAmount(
                                        amount = convertedCurrency,
                                        currencyCode = currency
                                    ),
                                    modifier = Modifier
                                        .animateItemPlacement()
                                        .fillMaxWidth()
                                        .padding(
                                            top = if (index == 0) 0.dp else 16.dp,
                                            bottom = if (index == result.data.lastIndex) 16.dp else 0.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Bottom sheet menu for currency selection
    if (showCurrencyChanger) {
        val currenciesList = if (currencies is Data.Successful)
            (currencies as Data.Successful<List<Currency>>).data else listOf(Currency())

        val selectedCurrency = if (changingCurrencyForCard == CardType.FROM)
            selectedFromCurrency else selectedToCurrency

        CurrencySelector(
            selectedCurrency = selectedCurrency,
            currencies = currenciesList,
            onCurrencySelected = {
                showCurrencyChanger = false
                if (changingCurrencyForCard == CardType.FROM)
                    viewModel.changeSelectedFromCurrency(it)
                else
                    viewModel.changeSelectedToCurrency(it)

            },
            onDismissRequest = {
                showCurrencyChanger = false
            }
        )
    }

    // Bottom sheet for About
    if (showInfoSheet) {
        AboutSheet() {
            showInfoSheet = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    ConvertoTheme {
        Surface {
            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = { }
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.round_swap_vert_24),
                    contentDescription = "Swap currency",
                )
            }
        }
    }
}